// Configuration.java, created on Apr 22, 2012
package eu.fabiostrozzi.dslog;

import java.util.Properties;

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
     * @param clazz
     * @return
     */
    public Adapter[] getAdaptersFor(Class<?> clazz);

    /**
     * Retrieves adapter-specific properties.
     * 
     * @param adapter
     *            The name of an adapter as declared in the adapter list.
     * @return
     */
    public Properties getAdapterProperties(String adapter);

}
