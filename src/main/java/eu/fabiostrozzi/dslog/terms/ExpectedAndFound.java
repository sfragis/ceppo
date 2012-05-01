// ExpectedAndFound.java, created on Apr 19, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * Expected and found details.
 * 
 * @author fabio
 */
public class ExpectedAndFound implements Term {
	private Object[] found;
	private Object[] expected;

	/**
	 * @return the found
	 */
	public Object[] getFound() {
		return found;
	}

	/**
	 * @param found
	 *            the found to set
	 */
	public void setFound(Object[] foundWhat) {
		this.found = foundWhat;
	}

	/**
	 * @return the expected
	 */
	public Object[] getExpected() {
		return expected;
	}

	/**
	 * @param expected
	 *            the expected to set
	 */
	public void setExpected(Object[] expectedWhat) {
		this.expected = expectedWhat;
	}
}
