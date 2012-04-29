// TermFormatter.java, created on Apr 30, 2012
package eu.fabiostrozzi.dslog.adapter;

import eu.fabiostrozzi.dslog.model.Term;

/**
 * Formats a term according to the current adapter.
 * 
 * @author fabio
 */
public interface TermFormatter<T extends Term> {
    /**
     * @param t
     * @return
     */
    public Object format(T t);
}
