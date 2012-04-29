// SubjectStatement.java, created on Apr 18, 2012
package eu.fabiostrozzi.dslog.lang;

/**
 * @author fabio
 * 
 */
public interface SubjectStatement {
    /**
     * A message identified by a message code.
     * 
     * @param msg
     * @return
     */
    MessageStatement message(String msg);

    /**
     * A user identified by a username.
     * 
     * @param username
     * @return
     */
    UserStatement user(String username);

    /**
     * @param constraint
     * @return
     */
    ConstraintStatement constraint(String constraint);

    /**
     * @param what
     * @return
     */
    ClosureStatement occurred(String what);

    /**
     * @param what
     * @param values
     * @return
     */
    ClosureStatement occurred(String what, Object... values);

    /**
     * @param throwable
     * @return
     */
    ClosureStatement occurred(Throwable throwable);

    /**
     * @param entity
     * @return
     */
    EntityStatement entity(String entity);
}
