// User.java, created on Apr 25, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class User implements Term {
    private String username;
    private boolean created = false;
    private boolean deleted = false;
    private boolean signedIn = false;
    private boolean signedOut = false;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the created
     */
    public boolean isCreated() {
        return created;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(boolean created) {
        this.created = created;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the signedIn
     */
    public boolean isSignedIn() {
        return signedIn;
    }

    /**
     * @param signedIn
     *            the signedIn to set
     */
    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    /**
     * @return the signedOut
     */
    public boolean isSignedOut() {
        return signedOut;
    }

    /**
     * @param signedOut
     *            the signedOut to set
     */
    public void setSignedOut(boolean signedOut) {
        this.signedOut = signedOut;
    }
}
