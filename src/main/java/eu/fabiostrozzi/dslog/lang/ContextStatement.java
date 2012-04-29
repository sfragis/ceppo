// ContextStatement.java, created on Apr 17, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 *
 */
public interface ContextStatement extends ClosureStatement {
	/**
     * Several attributes can be specified.
     * <p>
     * The number of specified attributes must be multiple of two as the
     * expected structure is an array made this way:<br>
     * <pre>{ "attribute1", value1, "attribute2", value2, ... }</pre>
     *
     */
    ClosureStatement with(Object...attributes);
}
