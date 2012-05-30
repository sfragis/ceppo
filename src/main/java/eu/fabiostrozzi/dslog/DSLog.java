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
     */
    private DSLog(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * @return
     */
    public Adapter[] getAdapters() {
        return instanceAdapters();
    }
    
    private Adapter[] instanceAdapters() {
        return adapters != null ? adapters : (adapters = DSLogConfig.getConfiguration().getAdaptersFor(clazz));
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
        return new DSLog(clazz);
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
        for (Adapter a : instanceAdapters())
            a.setUp(ctx);
        localContext.set(ctx);
    }

    /**
     * Tears down the per-thread context.
     */
    public void tearDown() {
        for (Adapter a : instanceAdapters())
            a.tearDown();
        localContext.remove();
    }
}
