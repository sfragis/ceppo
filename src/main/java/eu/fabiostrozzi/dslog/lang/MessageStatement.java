// MessageStatement.java, created on Apr 17, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface MessageStatement extends DirectionStatement {
    /**
     * @param content
     * @return
     */
    DirectionStatement content(String content);
}
