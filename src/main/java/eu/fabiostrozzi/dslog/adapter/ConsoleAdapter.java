// ConsoleAdapter.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog.adapter;

import static eu.fabiostrozzi.dslog.Utils.joinLines;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

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
                        sb.append(String.valueOf(o)).append(i % 2 == 0 ? "=" : ", ");
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
                // TODO Auto-generated method stub
            }
        });

        formatters.put(Happening.class, new TermFormatter<Happening>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                // TODO Auto-generated method stub
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
            public void format(Term t, StringBuilder sb) {
                // TODO Auto-generated method stub
            }
        });

        formatters.put(User.class, new TermFormatter<User>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * Default constructor is needed.
     */
    public ConsoleAdapter() {}

    /**
     * @param clazz
     */
    public ConsoleAdapter(Class<?> clazz) {
        this.clazz = clazz;
        this.dateFormat = DateFormat.getDateTimeInstance();
    }

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
    public boolean isGreatEqual(DSLogLevel level) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.Adapter#instanceFor(java.lang.Class)
     */
    @Override
    public Adapter instanceFor(Class<?> clazz) {
        return new ConsoleAdapter(clazz);
    }

}
