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

// ConsoleAdapter.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog.adapter;

import static eu.fabiostrozzi.dslog.Utils.joinLines;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import eu.fabiostrozzi.dslog.DSLogLevel;
import eu.fabiostrozzi.dslog.ThreadContext;
import eu.fabiostrozzi.dslog.terms.Constraint;
import eu.fabiostrozzi.dslog.terms.Context;
import eu.fabiostrozzi.dslog.terms.Entity;
import eu.fabiostrozzi.dslog.terms.ExpectedAndFound;
import eu.fabiostrozzi.dslog.terms.Happening;
import eu.fabiostrozzi.dslog.terms.Message;
import eu.fabiostrozzi.dslog.terms.Teller;
import eu.fabiostrozzi.dslog.terms.Term;
import eu.fabiostrozzi.dslog.terms.User;

/**
 * Console adapter uses {@code System.out.println}.
 * 
 * @author fabio
 */
public class ConsoleAdapter implements Adapter {
    private static final String SEPARATOR = " | ";
    private static final HashMap<Class<? extends Term>, TermFormatter<? extends Term>> formatters = new HashMap<Class<? extends Term>, TermFormatter<? extends Term>>();
    private Class<?> clazz;
    private DateFormat dateFormat;

    /**
     * Formats a term according to the current adapter.
     * 
     * @author fabio
     */
    private interface TermFormatter<T extends Term> {
        /**
         * @param sb
         * @param t
         * @return
         */
        public void format(Term term, StringBuilder sb);
    }

    static {
        formatters.put(Constraint.class, new TermFormatter<Constraint>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                Constraint c = (Constraint) t;
                sb.append("constraint '").append(c.getName()).append("' ")
                        .append(c.isPassed() ? "passed" : "violated");
                if (c.getPassedHow() != null)
                    sb.append(" due to ").append(c.getPassedHow());
            }
        });

        formatters.put(Context.class, new TermFormatter<Context>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                Context c = (Context) t;
                if (c.getAttributes().length > 0) {
                    sb.append("context is: ");
                    for (int i = 0; i < c.getAttributes().length; i++) {
                        Object o = c.getAttributes()[i];
                        String sep = i % 2 == 0 ? "=" : (i + 1 < c.getAttributes().length ? ", "
                                : "");
                        sb.append(String.valueOf(o)).append(sep);
                    }
                }
            }
        });

        formatters.put(Entity.class, new TermFormatter<Entity>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                Entity e = (Entity) t;
                sb.append(e.getName()).append(" ").append(e.getAction());
            }
        });

        formatters.put(ExpectedAndFound.class, new TermFormatter<ExpectedAndFound>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                ExpectedAndFound e = (ExpectedAndFound) t;
                if (e.getExpected() != null && e.getExpected().length > 0) {
                    sb.append("expected: ");
                    for (int i = 0; i < e.getExpected().length; i++) {
                        Object o = e.getExpected()[i];
                        String sep = i % 2 == 0 ? "="
                                : (i + 1 < e.getExpected().length ? ", " : "");
                        sb.append(String.valueOf(o)).append(sep);
                    }
                }
                if (e.getFound() != null && e.getFound().length > 0) {
                    sb.append("found: ");
                    for (int i = 0; i < e.getFound().length; i++) {
                        Object o = e.getFound()[i];
                        String sep = i % 2 == 0 ? "=" : (i + 1 < e.getFound().length ? ", " : "");
                        sb.append(String.valueOf(o)).append(sep);
                    }
                }
            }
        });

        formatters.put(Happening.class, new TermFormatter<Happening>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                Happening h = (Happening) t;
                sb.append("happened: ");
                if (h.getException() != null) {
                    sb.append("exception=");
                    sb.append(joinLines(h.getException().getMessage(), "§"));
                    sb.append(", stacktrace=");
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    h.getException().printStackTrace(pw);
                    sb.append(joinLines(sw.toString(), "§"));
                } else {
                    String s = h.getParams() != null && h.getParams().length > 0 ? String.format(
                            h.getWhat(), h.getParams()) : h.getWhat();
                    sb.append("what= ").append(s);
                }
            }
        });

        formatters.put(Message.class, new TermFormatter<Message>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                Message m = (Message) t;
                sb.append("message '").append(m.getType()).append("'");
                if (m.getTo() != null)
                    sb.append(" to '").append(m.getTo()).append("'");
                else
                    sb.append(" from '").append(m.getFrom()).append("'");
                if (m.getContent() != null)
                    sb.append(" with content '").append(joinLines(m.getContent(), "§")).append("'");
                switch (m.getStatus()) {
                case 1: // succeeded
                    sb.append(m.isSucceeded() ? " successfully " : " cannot be ").append(
                            m.getTo() != null ? " sent" : " received");
                    break;
                case 2: // prepared
                    sb.append(m.isPrepared() ? " successfully " : " cannot be ").append("prepared");
                    break;
                }
            }
        });

        formatters.put(Teller.class, new TermFormatter<Teller>() {
            @Override
            public void format(Term term, StringBuilder sb) {
                Teller t = (Teller) term;
                String s = t.getWhenParams() != null && t.getWhenParams().length > 0 ? String
                        .format(t.getWhen(), t.getWhenParams()) : t.getWhen();
                sb.append("when: ").append(s);
            }
        });

        formatters.put(User.class, new TermFormatter<User>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                User u = (User) t;
                sb.append("user '").append(u.getUsername()).append("'");
                if (u.isCreated())
                    sb.append(" created");
                else if (u.isDeleted())
                    sb.append(" deleted");
                else if (u.isSignedIn())
                    sb.append(" has signed in");
                else if (u.isSignedOut())
                    sb.append(" has signed out");
            }
        });
    }

    /**
     * Default constructor is needed.
     */
    public ConsoleAdapter() {}

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.Adapter#setUp(eu.fabiostrozzi.dslog.ThreadContext)
     */
    @Override
    public void setUp(ThreadContext context) {}

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.Adapter#tearDown()
     */
    @Override
    public void tearDown() {}

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.Adapter#log(eu.fabiostrozzi.dslog.DSLogLevel,
     * java.util.Date, eu.fabiostrozzi.dslog.ThreadContext, eu.fabiostrozzi.dslog.terms.Term[])
     */
    @Override
    public void log(DSLogLevel level, Date timestamp, ThreadContext context, Term... terms) {
        // sanity check on timestamp
        timestamp = timestamp != null ? timestamp : new Date();

        StringBuilder sb = new StringBuilder();
        sb.append(dateFormat.format(timestamp));

        sb.append(SEPARATOR);
        sb.append(timestamp.getTime());

        if (context != null) {
            sb.append(SEPARATOR);
            sb.append(context.getUUID());
        }
        sb.append(SEPARATOR);
        sb.append(clazz.getName());

        sb.append(SEPARATOR);
        sb.append(Thread.currentThread().getName());

        if (terms != null && terms.length > 0) {
            for (Term term : terms) {
                TermFormatter<? extends Term> tf = formatters.get(term.getClass());
                if (tf != null) {
                    sb.append(SEPARATOR);
                    tf.format(term, sb);
                }
            }
        }

        System.out.println(sb.toString());
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.Adapter#isGreatEqual(eu.fabiostrozzi.dslog.DSLogLevel)
     */
    @Override
    public boolean canLog(DSLogLevel level) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.Adapter#instanceFor(java.lang.Class)
     */
    @Override
    public Adapter of(Class<?> clazz) {
        ConsoleAdapter ca = new ConsoleAdapter();
        ca.clazz = clazz;
        ca.dateFormat = dateFormat;
        return ca;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.Adapter#setProperties(java.util.Properties)
     */
    @Override
    public void init(Properties props) {
        dateFormat = DateFormat.getDateTimeInstance();
    }
}
