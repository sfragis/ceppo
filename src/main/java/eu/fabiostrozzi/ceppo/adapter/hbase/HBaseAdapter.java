/*
 * Copyright 2012 Fabio Strozzi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// HBaseAdapter.java, created on May 26, 2012
package eu.fabiostrozzi.ceppo.adapter.hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import eu.fabiostrozzi.ceppo.CeppoConfig;
import eu.fabiostrozzi.ceppo.CeppoLevel;
import eu.fabiostrozzi.ceppo.ThreadContext;
import eu.fabiostrozzi.ceppo.adapter.Adapter;
import eu.fabiostrozzi.ceppo.terms.Constraint;
import eu.fabiostrozzi.ceppo.terms.Context;
import eu.fabiostrozzi.ceppo.terms.Entity;
import eu.fabiostrozzi.ceppo.terms.ExpectedAndFound;
import eu.fabiostrozzi.ceppo.terms.Happening;
import eu.fabiostrozzi.ceppo.terms.Message;
import eu.fabiostrozzi.ceppo.terms.Teller;
import eu.fabiostrozzi.ceppo.terms.Term;
import eu.fabiostrozzi.ceppo.terms.User;

/**
 * This adapter stores logs into HBase.
 * 
 * @author fabio
 */
public class HBaseAdapter implements Adapter {
    private static final byte[] DATA_COL_FAM = toBytes("d");
    private static final HashMap<Class<? extends Term>, ColumnBuilder<? extends Term>> columnBuilders = new HashMap<Class<? extends Term>, ColumnBuilder<? extends Term>>();

    private byte[] KEY_COL_FAM = toBytes("k");
    private byte[] KEY_ROW = toBytes(1);
    private byte[] KEY_ID = toBytes("id");

    private Class<?> clazz;
    private DateFormat dateFormat;
    private HBaseAdapterPool pool;
    private HBaseAdapterConfig config;

    /**
     * A column builder.
     * 
     * @author fabio
     */
    private interface ColumnBuilder<T extends Term> {

        /**
         * Adds as many columns as necessary for the given term
         * 
         * @param term
         *            The grammar term
         * @param put
         *            The HBase put command
         */
        public void addColumns(Term term, Put put);
    }

    static {
        columnBuilders.put(Constraint.class, new ColumnBuilder<Constraint>() {
            private final byte[] CONSTR_NAME = toBytes("constraint-name");
            private final byte[] CONSTR_PASSED = toBytes("constraint-passed");
            private final byte[] CONSTR_DUETO = toBytes("constraint-due-to");

            @Override
            public void addColumns(Term t, Put put) {
                Constraint c = (Constraint) t;
                put.add(DATA_COL_FAM, CONSTR_NAME, toBytes(c.getName()));
                put.add(DATA_COL_FAM, CONSTR_PASSED, toBytes(c.isPassed()));
                if (c.getPassedHow() != null)
                    put.add(DATA_COL_FAM, CONSTR_DUETO, toBytes(c.getPassedHow()));
            }
        });

        columnBuilders.put(Context.class, new ColumnBuilder<Context>() {
            @Override
            public void addColumns(Term t, Put put) {
                Context c = (Context) t;
                if (c.getAttributes().length > 0) {
                    for (int i = 0; i < c.getAttributes().length
                            && i + 1 < c.getAttributes().length; i += 2) {
                        Object name = c.getAttributes()[i];
                        Object val = c.getAttributes()[i + 1];
                        byte[] v = Bytes.toBytes(String.valueOf(val));
                        String col = "ctx-" + String.valueOf(name);
                        put.add(DATA_COL_FAM, toBytes(col), v);
                    }
                }
            }
        });

        columnBuilders.put(Entity.class, new ColumnBuilder<Entity>() {
            private final byte[] ENTITY_NAME = toBytes("entity-name");
            private final byte[] ENTITY_ACTION = toBytes("entity-action");

            @Override
            public void addColumns(Term t, Put put) {
                Entity e = (Entity) t;
                put.add(DATA_COL_FAM, ENTITY_NAME, toBytes(e.getName()));
                put.add(DATA_COL_FAM, ENTITY_ACTION, toBytes(e.getAction()));
            }
        });

        columnBuilders.put(ExpectedAndFound.class, new ColumnBuilder<ExpectedAndFound>() {
            @Override
            public void addColumns(Term t, Put put) {
                ExpectedAndFound e = (ExpectedAndFound) t;
                if (e.getExpected() != null && e.getExpected().length > 0) {
                    for (int i = 0; i < e.getExpected().length && i + 1 < e.getExpected().length; i += 2) {
                        Object name = e.getExpected()[i];
                        Object val = e.getExpected()[i + 1];
                        byte[] v = toBytes(String.valueOf(val));
                        String col = "expected-" + String.valueOf(name);
                        put.add(DATA_COL_FAM, toBytes(col), v);
                    }
                }
                if (e.getFound() != null && e.getFound().length > 0) {
                    for (int i = 0; i < e.getFound().length && i + 1 < e.getFound().length; i += 2) {
                        Object name = e.getFound()[i];
                        Object val = e.getFound()[i + 1];
                        byte[] v = toBytes(String.valueOf(val));
                        String col = "found-" + String.valueOf(name);
                        put.add(DATA_COL_FAM, toBytes(col), v);
                    }
                }
            }
        });

        columnBuilders.put(Happening.class, new ColumnBuilder<Happening>() {
            private final byte[] EXCEPTION = toBytes("exception");
            private final byte[] STACKTRACE = toBytes("stacktrace");
            private final byte[] HAPPENED = toBytes("happened");

            @Override
            public void addColumns(Term t, Put put) {
                Happening h = (Happening) t;
                if (h.getException() != null) {
                    put.add(DATA_COL_FAM, EXCEPTION, toBytes(h.getException().getMessage()));
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    h.getException().printStackTrace(pw);
                    put.add(DATA_COL_FAM, STACKTRACE, toBytes(sw.toString()));
                } else {
                    String s = h.getParams() != null && h.getParams().length > 0 ? String.format(
                            h.getWhat(), h.getParams()) : h.getWhat();
                    put.add(DATA_COL_FAM, HAPPENED, toBytes(s));
                }
            }
        });

        columnBuilders.put(Message.class, new ColumnBuilder<Message>() {
            private final byte[] MESSAGE = toBytes("message");
            private final byte[] TO = toBytes("message-to");
            private final byte[] FROM = toBytes("message-from");
            private final byte[] CONTENT = toBytes("message-content");
            private final byte[] PREPARED = toBytes("message-prepared");
            private final byte[] SENT = toBytes("message-sent");
            private final byte[] RECEIVED = toBytes("message-received");

            @Override
            public void addColumns(Term t, Put put) {
                Message m = (Message) t;
                put.add(DATA_COL_FAM, MESSAGE, toBytes(m.getType()));

                if (m.getTo() != null)
                    put.add(DATA_COL_FAM, TO, toBytes(m.getTo()));
                else
                    put.add(DATA_COL_FAM, FROM, toBytes(m.getFrom()));
                if (m.getContent() != null)
                    put.add(DATA_COL_FAM, CONTENT, toBytes(m.getContent()));
                switch (m.getStatus()) {
                case 1: // sent/received?
                    put.add(DATA_COL_FAM, m.getTo() != null ? SENT : RECEIVED,
                            toBytes(m.isSucceeded()));
                    break;
                case 2: // prepared?
                    put.add(DATA_COL_FAM, PREPARED, toBytes(m.isPrepared()));
                    break;
                }
            }
        });

        columnBuilders.put(Teller.class, new ColumnBuilder<Teller>() {
            private final byte[] WHEN = toBytes("when");

            @Override
            public void addColumns(Term term, Put put) {
                Teller t = (Teller) term;
                String s = t.getWhenParams() != null && t.getWhenParams().length > 0 ? String
                        .format(t.getWhen(), t.getWhenParams()) : t.getWhen();
                put.add(DATA_COL_FAM, WHEN, toBytes(s));
            }
        });

        columnBuilders.put(User.class, new ColumnBuilder<User>() {
            private final byte[] USER = toBytes("user");
            private final byte[] USER_CREATED = toBytes("user-created");
            private final byte[] USER_DELETED = toBytes("user-deleted");
            private final byte[] USER_SIGNED_IN = toBytes("user-signed-in");
            private final byte[] USER_SIGNED_OUT = toBytes("user-signed-out");

            @Override
            public void addColumns(Term t, Put put) {
                User u = (User) t;
                put.add(DATA_COL_FAM, USER, toBytes(u.getUsername()));
                if (u.isCreated())
                    put.add(DATA_COL_FAM, USER_CREATED, toBytes(true));
                else if (u.isDeleted())
                    put.add(DATA_COL_FAM, USER_DELETED, toBytes(true));
                else if (u.isSignedIn())
                    put.add(DATA_COL_FAM, USER_SIGNED_IN, toBytes(true));
                else if (u.isSignedOut())
                    put.add(DATA_COL_FAM, USER_SIGNED_OUT, toBytes(true));
            }
        });
    }

    /**
     * 
     */
    public HBaseAdapter() {}

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.Adapter#setUp(eu.fabiostrozzi.ceppo.ThreadContext)
     */
    @Override
    public void setUp(ThreadContext context) {}

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.Adapter#tearDown()
     */
    @Override
    public void tearDown() {}

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.Adapter#log(eu.fabiostrozzi.ceppo.CeppoLevel,
     * java.util.Date, eu.fabiostrozzi.ceppo.ThreadContext, eu.fabiostrozzi.ceppo.terms.Term[])
     */
    @Override
    public void log(CeppoLevel level, Date timestamp, ThreadContext context, Term... terms) {
        // sanity check on timestamp
        timestamp = timestamp != null ? timestamp : new Date();

        String uuid = context.getUUID().toString();
        String node = CeppoConfig.getConfiguration().getNode();

        HTableInterface tab = null;
        try {
            tab = pool.getTable(config.getTable());
            long id = tab.incrementColumnValue(KEY_ROW, KEY_COL_FAM, KEY_ID, 1);

            Put put = new Put(toBytes(id));

            // sets the application node
            put.add(DATA_COL_FAM, toBytes("node"), toBytes(node));

            // sets the UUID
            put.add(DATA_COL_FAM, toBytes("uuid"), toBytes(uuid));

            // sets applicative timestamp
            put.add(DATA_COL_FAM, toBytes("ts"), toBytes(timestamp.getTime()));

            // sets class name
            put.add(DATA_COL_FAM, toBytes("class"), toBytes(clazz.getCanonicalName()));

            // sets the thread name
            put.add(DATA_COL_FAM, toBytes("thread"), toBytes(Thread.currentThread().getName()));

            // term-specific columns
            if (terms != null && terms.length > 0) {
                for (Term term : terms) {
                    ColumnBuilder<? extends Term> cb = columnBuilders.get(term.getClass());
                    if (cb != null)
                        cb.addColumns(term, put);
                }
            }

            // sends to HBase
            ArrayList<Put> puts = new ArrayList<Put>(1);
            puts.add(put);
            tab.put(puts);
        } catch (Exception e) {
            // TODO: log exception with a warning
            e.printStackTrace(System.err);
        } finally {
            try {
                if (tab != null)
                    tab.close();
            } catch (IOException e) {
                // TODO: log exception with a warning
                e.printStackTrace(System.err);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.Adapter#isGreatEqual(eu.fabiostrozzi.ceppo.CeppoLevel)
     */
    @Override
    public boolean canLog(CeppoLevel level) {
        return config.getLevel().isLowerEqual(level);
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.Adapter#instanceFor(java.lang.Class)
     */
    @Override
    public Adapter of(Class<?> clazz) {
        HBaseAdapter hba = new HBaseAdapter();
        hba.clazz = clazz;
        hba.pool = pool;
        hba.config = config;
        hba.dateFormat = dateFormat;
        return hba;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.Adapter#init(java.util.Properties)
     */
    @Override
    public void init(Properties properties) {
        dateFormat = DateFormat.getDateTimeInstance();
        config = new HBaseAdapterConfig(properties);
        pool = config.instancePool();
        pool.initPool(properties, config.getPoolSize());
    }

}
