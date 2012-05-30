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

// Adapter.java, created on Apr 24, 2012
package eu.fabiostrozzi.ceppo.adapter;

import java.util.Date;
import java.util.Properties;

import eu.fabiostrozzi.ceppo.CeppoLevel;
import eu.fabiostrozzi.ceppo.ThreadContext;
import eu.fabiostrozzi.ceppo.terms.Term;

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
    void log(CeppoLevel level, Date timestamp, ThreadContext context, Term... terms);

    /**
     * Returns true if the log level of the underlying log device is smaller than the specified
     * input log level.
     * <p>
     * For instance, the following scenarios may happen:
     * <li>the adapter level is debug and the passed level is info, then the log is passed to the
     * adapter</li>
     * <li>the adapter level is warning and the passed level is debug, then the log is NOT passed to
     * the adapter</li>
     * <li>the adapter level is info and the passed level is info, then the log IS passed to the
     * adapter</li>
     * 
     * 
     * @param level
     * @return
     */
    boolean canLog(CeppoLevel level);

    /**
     * Instances this adapter anew for the specified class if this adapter is class-binded.
     * <p>
     * Otherwise it simply returns this adapter.
     * 
     * @param clazz
     */
    Adapter of(Class<?> clazz);

    /**
     * @param props
     */
    public void init(Properties props);
}
