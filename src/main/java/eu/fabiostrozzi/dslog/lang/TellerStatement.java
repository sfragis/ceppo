// TellerStatement.java, created on Apr 18, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface TellerStatement extends SubjectStatement {
	/**
	 * @param when
	 * @return
	 */
	SubjectStatement when(String when);

	/**
	 * @param format
	 * @param params
	 * @return
	 */
	SubjectStatement when(String format, Object[] params);

	/**
	 * @param when
	 * @return
	 */
	SubjectStatement during(String when);

	/**
	 * @param format
	 * @param params
	 * @return
	 */
	SubjectStatement during(String format, Object[] params);
}
