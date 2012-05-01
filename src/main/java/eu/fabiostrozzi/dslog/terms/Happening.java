// Happening.java, created on Apr 19, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class Happening implements Term {
    private String what;
    private Throwable exception;
    private Object[] params;

    /**
     * @return the what
     */
    public String getWhat() {
        return what;
    }

    /**
     * @param what
     *            the what to set
     */
    public void setWhat(String what) {
        this.what = what;
    }

    /**
     * @return the exception
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * @param exception
     *            the exception to set
     */
    public void setException(Throwable throwable) {
        this.exception = throwable;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(Object[] params) {
        this.params = params;
    }
}
