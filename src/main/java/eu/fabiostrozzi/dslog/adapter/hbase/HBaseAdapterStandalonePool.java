// HBaseAdapterStandalonePool.java, created on May 27, 2012
package eu.fabiostrozzi.dslog.adapter.hbase;

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
     * @see eu.fabiostrozzi.dslog.adapter.HBaseAdapterPool#getTable(java.lang.String)
     */
    @Override
    public HTableInterface getTable(String tableName) {
        return pool.getTable(tableName);
    }

    /*
     * (non-Javadoc)
     * @see eu.fabiostrozzi.dslog.adapter.HBaseAdapterPool#getTable(byte[])
     */
    @Override
    public HTableInterface getTable(byte[] tableName) {
        return pool.getTable(tableName);
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.fabiostrozzi.dslog.adapter.hbase.HBaseAdapterPool#initPool(eu.fabiostrozzi.dslog.adapter
     * .hbase.HBaseAdapterConfig, java.util.Properties)
     */
    @Override
    public void initPool(Properties properties, int poolSize) {
        Configuration config = HBaseConfiguration.create();
        this.pool = new HTablePool(config, poolSize);
    }

}
