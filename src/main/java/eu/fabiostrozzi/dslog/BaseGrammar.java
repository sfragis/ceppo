// BaseGrammar.java, created on Apr 25, 2012
package eu.fabiostrozzi.dslog;

import java.util.ArrayList;

import eu.fabiostrozzi.dslog.adapter.Adapter;
import eu.fabiostrozzi.dslog.model.Term;

/**
 * @author fabio
 * 
 */
public abstract class BaseGrammar {
    protected DSLogLevel level;
    protected ArrayList<Term> terms;

    /**
     * Instances a new basic grammar with the specified log level.
     * 
     * @param level
     */
    public BaseGrammar(DSLogLevel level) {
        this.level = level;
        this.terms = new ArrayList<Term>(5);
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
                a.log(level, log.getContext(), array);
            }
        }
    }
}
