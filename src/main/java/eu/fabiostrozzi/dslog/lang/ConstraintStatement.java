// ConstraintStatement.java, created on Apr 18, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface ConstraintStatement {
	/**
	 * @return
	 */
	ExpectedStatement passed();

	/**
	 * @param how
	 * @return
	 */
	ExpectedStatement passed(String how);

	/**
	 * @return
	 */
	ExpectedStatement violated();

	/**
	 * @param how
	 * @return
	 */
	ExpectedStatement violated(String how);
}
