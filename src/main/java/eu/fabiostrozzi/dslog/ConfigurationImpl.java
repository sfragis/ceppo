/*
 * Copyright 2012 Fabio Strozzi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
