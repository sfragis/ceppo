// DSLog.java, created on Apr 17, 2012
package eu.fabiostrozzi.dslog;

import eu.fabiostrozzi.dslog.adapter.Adapter;
import eu.fabiostrozzi.dslog.lang.TellerStatement;

/**
 * Domain specific log language.
 * 
 * @author fabio
 */
public class DSLog {
    private static final ThreadLocal<ThreadContext> localContext = new ThreadLocal<ThreadContext>();

    private Class<?> clazz;
    private Adapter[] adapters;

    /**
     * @param clazz
     * @param adapters
     */
    private DSLog(Class<?> clazz, Adapter[] adapters) {
        this.clazz = clazz;
        this.adapters = adapters;
    }

    /**
     * @return
     */
    public Adapter[] getAdapters() {
        return adapters;
    }

    /**
     * @return
     */
    public Class<?> getClazz() {
        return this.clazz;
    }

    public ThreadContext getContext() {
        return localContext.get();
    }

    /**
     * Instances a new log class.
     * 
     * @param clazz
     * @return
     */
    public static <S> DSLog get(Class<S> clazz) {
        Adapter[] adapters = DSLogConfig.getConfiguration().getAdapters();
        Adapter[] instances = new Adapter[adapters.length];
        int i = 0;
        for (Adapter a : adapters)
            instances[i++] = a.instanceFor(clazz);
        return new DSLog(clazz, instances);
    }

    /**
     * Use this level for unexpected exceptions and strange behaviours.
     * 
     * @return
     */
    public static TellerStatement fatal() {
        return new LogGrammar(DSLogLevel.FATAL);
    }

    /**
     * Use this level for functional errors, not unexpected ones (in which case use
     * {@link DSLogLevel#FATAL}).
     * 
     * @return
     */
    public static TellerStatement error() {
        return new LogGrammar(DSLogLevel.ERROR);
    }

    /**
     * Use this level to warn about non-blocking happenings.
     * 
     * @return
     */
    public static TellerStatement warn() {
        return new LogGrammar(DSLogLevel.WARNING);
    }

    /**
     * Use this level to inform about commmon happenings.
     * 
     * @return
     */
    public static TellerStatement info() {
        return new LogGrammar(DSLogLevel.INFO);
    }

    /**
     * Use this level to inform about low importance happenings.
     * 
     * @return
     */
    public static TellerStatement debug() {
        return new LogGrammar(DSLogLevel.DEBUG);
    }

    /**
     * Sets up the per-thread context.
     * <p>
     * This and the <code>tearDown</code> method should be used in a try-finally manner:
     * 
     * <pre>
     * try {
     *     DSLog.setUp();
     *     // do what must be done
     * } finally {
     *     DSLog.tearDown();
     * }
     * </pre>
     * 
     * This way the diagnostic context will always be cleared after usage.
     */
    public void setUp() {
        ThreadContext ctx = new ThreadContext();
        for (Adapter a : adapters)
            a.setUp(ctx);
        localContext.set(ctx);
    }

    /**
     * Tears down the per-thread context.
     */
    public void tearDown() {
        for (Adapter a : adapters)
            a.tearDown();
        localContext.remove();
    }
}
