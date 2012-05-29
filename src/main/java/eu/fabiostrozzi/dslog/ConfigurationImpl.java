// ConfigurationImpl.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog;

import java.util.Hashtable;
import java.util.Properties;

import eu.fabiostrozzi.dslog.adapter.Adapter;

/**
 * Configuration implementation.
 * 
 * @author fabio
 */
public class ConfigurationImpl implements Configuration {
    private Adapter[] adapters;
    private String node;
    private Hashtable<String, Properties> adapterProperties;

    public ConfigurationImpl() {
        this.adapterProperties = new Hashtable<String, Properties>(2);
    }

    /**
     * @param clazz
     * @return the adapters
     */
    @Override
    public Adapter[] getAdaptersFor(Class<?> clazz) {
        Adapter[] instances = new Adapter[adapters.length];
        int i = 0;
        for (Adapter a : adapters)
            instances[i++] = a.of(clazz);
        return instances;
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
    @Override
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

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.Configuration#getAdapterProperties(java.lang.String)
     */
    @Override
    public Properties getAdapterProperties(String adapter) {
        return adapterProperties.get(adapter);
    }

    /**
     * @param adapter
     * @param adapterProp
     */
    public void setAdapterProperties(String adapter, Properties adapterProp) {
        this.adapterProperties.put(adapter, adapterProp);
    }

}
