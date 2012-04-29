// Configuration.java, created on Apr 22, 2012
package eu.fabiostrozzi.dslog;

import eu.fabiostrozzi.dslog.adapter.Adapter;

/**
 * DSLog settings.
 * 
 * @author fabio
 */
public interface Configuration {
    /**
     * Get the name of the current application node in a distributed system.
     * 
     * @return The name of the current application node in a distribute system.
     */
    public String getNode();

    /**
     * @return
     */
    public Adapter[] getAdapters();

}
