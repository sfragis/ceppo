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
            if (a.isGreatEqual(level)) {
                if (array == null)
                    array = terms.toArray(new Term[0]);
                a.log(level, timestamp, log.getContext(), array);
            }
        }
    }
}
