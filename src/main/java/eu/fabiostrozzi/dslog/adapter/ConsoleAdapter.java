// ConsoleAdapter.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog.adapter;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import eu.fabiostrozzi.dslog.DSLogLevel;
import eu.fabiostrozzi.dslog.ThreadContext;
import eu.fabiostrozzi.dslog.model.Constraint;
import eu.fabiostrozzi.dslog.model.Context;
import eu.fabiostrozzi.dslog.model.Entity;
import eu.fabiostrozzi.dslog.model.Event;
import eu.fabiostrozzi.dslog.model.ExpectedAndFound;
import eu.fabiostrozzi.dslog.model.Happening;
import eu.fabiostrozzi.dslog.model.Message;
import eu.fabiostrozzi.dslog.model.Teller;
import eu.fabiostrozzi.dslog.model.Term;
import eu.fabiostrozzi.dslog.model.User;

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
                    sb.append("context is made up of: ");
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

        formatters.put(Event.class, new TermFormatter<Event>() {
            @Override
            public void format(Term t, StringBuilder sb) {
                Event e = (Event) t;
                // TODO
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
                // TODO Auto-generated method stub
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
     * java.util.Date, eu.fabiostrozzi.dslog.ThreadContext, eu.fabiostrozzi.dslog.model.Term[])
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
