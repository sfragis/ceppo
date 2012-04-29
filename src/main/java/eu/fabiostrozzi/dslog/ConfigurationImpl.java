// ConfigurationImpl.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog;

import eu.fabiostrozzi.dslog.adapter.Adapter;

/**
 * @author fabio
 * 
 */
public class ConfigurationImpl implements Configuration {
    private Adapter[] adapters;
    private String node;

    /**
     * @return the adapters
     */
    public Adapter[] getAdapters() {
        return adapters;
    }

    /**
     * @param adapters
     *            the adapters to set
     */
    public void setAdapters(Adapter[] adapters) {
        this.adapters = adapters;
    }

    /**
     * @return the node
     */
    public String getNode() {
        return node;
    }

    /**
     * @param node
     *            the node to set
     */
    public void setNode(String node) {
        this.node = node;
    }

}
