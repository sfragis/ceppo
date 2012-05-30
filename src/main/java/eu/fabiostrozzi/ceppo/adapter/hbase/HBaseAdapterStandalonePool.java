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

// HBaseAdapterStandalonePool.java, created on May 27, 2012
package eu.fabiostrozzi.ceppo.adapter.hbase;

import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;

/**
 * @author fabio
 * 
 */
public class HBaseAdapterStandalonePool implements HBaseAdapterPool {
    private HTablePool pool;

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.HBaseAdapterPool#getTable(java.lang.String)
     */
    @Override
    public HTableInterface getTable(String tableName) {
        return pool.getTable(tableName);
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.ceppo.adapter.HBaseAdapterPool#getTable(byte[])
     */
    @Override
    public HTableInterface getTable(byte[] tableName) {
        return pool.getTable(tableName);
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.fabiostrozzi.ceppo.adapter.hbase.HBaseAdapterPool#initPool(eu.fabiostrozzi.ceppo.adapter
     * .hbase.HBaseAdapterConfig, java.util.Properties)
     */
    @Override
    public void initPool(Properties properties, int poolSize) {
        Configuration config = HBaseConfiguration.create();
        this.pool = new HTablePool(config, poolSize);
    }

}
