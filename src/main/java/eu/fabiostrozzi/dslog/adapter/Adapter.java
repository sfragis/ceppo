// Adapter.java, created on Apr 24, 2012
package eu.fabiostrozzi.dslog.adapter;

import java.util.Date;

import eu.fabiostrozzi.dslog.ThreadContext;
import eu.fabiostrozzi.dslog.DSLogLevel;
import eu.fabiostrozzi.dslog.model.Term;

/**
 * Defines an adapter for a generic log device.
 * 
 * @author fabio
 */
public interface Adapter {
    /**
     * Sets the log context up.
     * <p>
     * This method will likely implement a static call to the adapter MDC.
     * 
     * @param context
     */
    void setUp(ThreadContext context);

    /**
     * Tears down the log context.
     * <p>
     * This method will likely implement a static call to the adapter MDC.
     */
    void tearDown();

    /**
     * Tells the underlying log device to compose a message made of the given terms.
     * 
     * @param level
     * @param timestamp 
     * @param context
     * @param terms
     */
    void log(DSLogLevel level, Date timestamp, ThreadContext context, Term... terms);

    /**
     * Returns true if the log level of the underlying log device is at least higher as input value.
     * 
     * @param level
     * @return
     */
    boolean isGreatEqual(DSLogLevel level);

    /**
     * Instances this adapter anew for the specified class if this adapter is class-binded.
     * <p>
     * Otherwise it simply returns this adapter.
     * 
     * @param clazz
     */
    Adapter instanceFor(Class<?> clazz);
}
