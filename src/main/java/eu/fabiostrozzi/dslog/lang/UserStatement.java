// User.java, created on Apr 25, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * Tells something about a user.
 * 
 * @author fabio
 */
public interface UserStatement {

    /**
     * @return
     */
    ContextStatement created();

    /**
     * @return
     */
    ContextStatement deleted();

    /**
     * @return
     */
    ContextStatement signedIn();

    /**
     * @return
     */
    ContextStatement signedOut();
}
