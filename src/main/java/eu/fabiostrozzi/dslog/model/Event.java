// Event.java, created on Apr 20, 2012
package eu.fabiostrozzi.dslog.model;

/**
 * @author fabio
 * 
 */
public class Event implements Term {
	private boolean succeeded;
	private String outcome;
	private boolean prepared = false;

	/**
	 * @return the succeeded
	 */
	public boolean isSucceeded() {
		return succeeded;
	}

	/**
	 * @param succeeded
	 *            the succeeded to set
	 */
	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome
	 *            the outcome to set
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	/**
	 * @return the prepared
	 */
	public boolean isPrepared() {
		return prepared;
	}

	/**
	 * @param prepared
	 *            the prepared to set
	 */
	public void setPrepared(boolean prepared) {
		this.prepared = prepared;
	}
}
