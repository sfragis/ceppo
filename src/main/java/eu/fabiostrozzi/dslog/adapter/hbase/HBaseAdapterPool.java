// HBaseAdapterPool.java, created on May 27, 2012
package eu.fabiostrozzi.dslog.adapter.hbase;

import java.util.Properties;

import org.apache.hadoop.hbase.client.HTableInterface;

/**
 * @author fabio
 */
public interface HBaseAdapterPool {
    /**
     * @param tableName
     * @return
     */
    HTableInterface getTable(String tableName);

    /**
     * @param tablesName
     * @return
     */
    HTableInterface getTable(byte[] tableName);

    /**
     * @param properties
     * @param poolSize
     */
    void initPool(Properties properties, int poolSize);
}
