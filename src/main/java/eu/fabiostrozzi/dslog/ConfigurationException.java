// ConfigurationException.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog;

/**
 * DSLog configuration exception.
 * 
 * @author fabio
 */
public class ConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 2764941928042879361L;

    /**
     * 
     */
    public ConfigurationException() {}

    /**
     * @param arg0
     */
    public ConfigurationException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public ConfigurationException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ConfigurationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
