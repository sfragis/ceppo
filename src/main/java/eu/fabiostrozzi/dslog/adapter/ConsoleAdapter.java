// ConsoleAdapter.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog.adapter;

import java.text.DateFormat;
import java.util.Date;

import eu.fabiostrozzi.dslog.ThreadContext;
import eu.fabiostrozzi.dslog.DSLogLevel;
import eu.fabiostrozzi.dslog.model.Term;

/**
 * Console adapter uses {@code System.out.println}.
 * 
 * @author fabio
 */
public class ConsoleAdapter implements Adapter {
    private static final String SEPARATOR = " | ";
    private Class<?> clazz;
    private DateFormat dateFormat;

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
     * eu.fabiostrozzi.dslog.ThreadContext, eu.fabiostrozzi.dslog.model.Term[])
     */
    @Override
    public void log(DSLogLevel level, ThreadContext context, Term... terms) {
        StringBuilder sb = new StringBuilder();
        sb.append(dateFormat.format(new Date()));

        sb.append(SEPARATOR);
        sb.append(System.currentTimeMillis());

        if (context != null) {
            sb.append(SEPARATOR);
            sb.append(context.getUUID());
        }
        sb.append(SEPARATOR);
        sb.append(clazz.getName());

        if (terms != null && terms.length > 0) {
            for (Term term : terms) {
                sb.append(SEPARATOR);

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
