// Message.java, created on Apr 20, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class Message implements Term {
    private String type;
    private String from;
    private String to;
    private String content;
    private boolean succeeded;
    private String why;
    private boolean prepared;

    // 0 = none set
    // 1 = succeeded
    // 2 = prepared
    private int status = 0;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

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
        this.status = 1;
    }

    /**
     * @return the why
     */
    public String getWhy() {
        return why;
    }

    /**
     * @param why
     *            the why to set
     */
    public void setWhy(String why) {
        this.why = why;
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
        this.status = 2;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

}
