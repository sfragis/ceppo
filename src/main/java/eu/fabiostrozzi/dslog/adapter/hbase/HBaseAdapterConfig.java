// HBaseAdapterConfig.java, created on May 27, 2012
package eu.fabiostrozzi.dslog.adapter.hbase;

import static java.lang.String.format;

import java.util.Properties;

import eu.fabiostrozzi.dslog.ConfigurationException;
import eu.fabiostrozzi.dslog.DSLogLevel;
import eu.fabiostrozzi.dslog.Utils;

/**
 * Configuration class for HBase adapter.
 * 
 * @author fabio
 */
public class HBaseAdapterConfig {
    private static final String LEVEL_PROP = "level";
    private static final String TABLE_PROP = "table";
    private static final String POOL_CLASS_PROP = "pool.class";
    private static final String POOL_SIZE_PROP = "pool.size";

    private static final Class<? extends HBaseAdapterPool> POOL_CLASS_DFLT = HBaseAdapterStandalonePool.class;
    private static final int POOL_SIZE_DFLT = 10;
    private static final DSLogLevel LEVEL_DFLT = DSLogLevel.INFO;
    private static final String TABLE_DFLT = "dslog";

    private Properties properties;
    private HBaseAdapterPool pool;

    /**
     * @param properties
     */
    public HBaseAdapterConfig(Properties properties) {
        this.properties = properties;
    }

    /**
     * @return
     */
    public HBaseAdapterPool instancePool() {
        return pool != null ? pool : (pool = newPool());
    }

    /**
     * @return
     */
    public DSLogLevel getLevel() {
        String sl = Utils.getString(properties, LEVEL_PROP, LEVEL_DFLT.name());
        DSLogLevel l = DSLogLevel.of(sl);
        return l != null ? l : LEVEL_DFLT;
    }

    /**
     * @return
     */
    public int getPoolSize() {
        return Utils.getInt(properties, POOL_SIZE_PROP, POOL_SIZE_DFLT);
    }

    /**
     * @return
     */
    public String getTable() {
        return Utils.getString(properties, TABLE_PROP, TABLE_DFLT);
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    private HBaseAdapterPool newPool() {
        // gets the proper pool implementation
        Class<? extends HBaseAdapterPool> clazz;
        String fqcn = properties.getProperty(POOL_CLASS_PROP);
        if (fqcn == null || fqcn.trim().equals(""))
            clazz = POOL_CLASS_DFLT;
        else {
            try {
                clazz = (Class<HBaseAdapterPool>) Class.forName(fqcn.trim());
            } catch (ClassNotFoundException e) {
                throw new ConfigurationException(format("No class qualified as '%s' was found",
                        fqcn), e);
            }
        }

        // instances the pool
        HBaseAdapterPool p = Utils.instanceOf(clazz);

        return p;
    }

}
