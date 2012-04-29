// MessageContentStatement.java, created on Apr 25, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface DirectionStatement {

    /**
     * @param from
     * @return
     */
    EventStatement from(String from);

    /**
     * @param to
     * @return
     */
    EventStatement to(String to);
}
