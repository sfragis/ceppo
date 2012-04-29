// EventStatement.java, created on Apr 17, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface EventStatement {
	/**
	 * @return
	 */
	ContextStatement failed();

	/**
	 * @param how
	 * @return
	 */
	ContextStatement failed(String how);

	/**
	 * @return
	 */
	ContextStatement succeeded();

	/**
	 * @param how
	 * @return
	 */
	ContextStatement succeeded(String how);

	/**
	 * @return
	 */
	ContextStatement prepared();
}
