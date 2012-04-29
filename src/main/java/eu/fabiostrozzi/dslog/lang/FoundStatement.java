// FoundStatement.java, created on Apr 18, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface FoundStatement {
	/**
	 * @param what
	 * @return
	 */
	ClosureStatement found(Object... what);
}
