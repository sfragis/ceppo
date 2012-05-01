// Context.java, created on Apr 20, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class Context implements Term {
	private Object[] attributes;

	/**
	 * @return the attributes
	 */
	public Object[] getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the context attributes to set
	 */
	public void setAttributes(Object[] attributes) {
		this.attributes = attributes;
	}
}
