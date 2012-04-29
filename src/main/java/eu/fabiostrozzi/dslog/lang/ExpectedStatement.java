// ExpectedStatement.java, created on Apr 17, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface ExpectedStatement extends ClosureStatement {
	/**
	 * @param what
	 * @return
	 */
	FoundStatement expected(Object... what);
}
