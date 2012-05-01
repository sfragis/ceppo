// Teller.java, created on Apr 20, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class Teller implements Term {
	private String when;
	private Object[] whenParams;

	/**
	 * @return the when
	 */
	public String getWhen() {
		return when;
	}

	/**
	 * @param when
	 *            the when to set
	 */
	public void setWhen(String when) {
		this.when = when;
	}

	/**
	 * @return the whenParams
	 */
	public Object[] getWhenParams() {
		return whenParams;
	}

	/**
	 * @param whenParams
	 *            the whenParams to set
	 */
	public void setWhenParams(Object[] whenParams) {
		this.whenParams = whenParams;
	}
}
