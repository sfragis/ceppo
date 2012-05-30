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
