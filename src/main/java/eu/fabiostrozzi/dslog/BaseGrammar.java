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

// BaseGrammar.java, created on Apr 25, 2012
package eu.fabiostrozzi.dslog;

import java.util.ArrayList;
import java.util.Date;

import eu.fabiostrozzi.dslog.adapter.Adapter;
import eu.fabiostrozzi.dslog.terms.Term;

/**
 * @author fabio
 * 
 */
public abstract class BaseGrammar {
    protected DSLogLevel level;
    protected ArrayList<Term> terms;
    protected Date timestamp;

    /**
     * Instances a new basic grammar with the specified log level.
     * 
     * @param level
     */
    public BaseGrammar(DSLogLevel level) {
        this.level = level;
        this.terms = new ArrayList<Term>(5);
        this.timestamp = new Date();
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.lang.ClosureStatement#log(eu.fabiostrozzi.dslog .DSLog)
     */
    public void log(DSLog log) {
        Adapter[] adapters = log.getAdapters();
        Term[] array = null;
        for (Adapter a : adapters) {
            if (a.canLog(level)) {
                if (array == null)
                    array = terms.toArray(new Term[0]);
                a.log(level, timestamp, log.getContext(), array);
            }
        }
    }
}
