// DSLogConfig.java, created on Apr 29, 2012
package eu.fabiostrozzi.dslog;

import static eu.fabiostrozzi.dslog.Utils.getStrings;
import static eu.fabiostrozzi.dslog.Utils.instancesOf;
import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Properties;

import eu.fabiostrozzi.dslog.adapter.Adapter;

/**
 * Configuration class.
 * 
 * @author fabio
 */
public class DSLogConfig {
    private static final String NODE_PROP = "node";
    private static final String ADAPTERS_PROP = "adapters";
    private static final String ADAPTER_PREF_PROP = "adapter";

    private static Configuration configuration = null;

    /**
     * @return
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Configures DSLog.
     * 
     * @param propFile
     * @throws IOException
     */
    public static void configure(String propFile) throws IOException {
        if (configuration == null) {
            Reader reader = new BufferedReader(new FileReader(new File(propFile)));
            Properties props = new Properties();
            props.load(reader);
            configuration = configure(props);
        }
    }

    /**
     * @param is
     * @throws IOException
     */
    public static void configure(InputStream is) throws IOException {
        if (configuration == null) {
            Properties props = new Properties();
            props.load(is);
            configuration = configure(props);
        }
    }

    /**
     * @param props
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Configuration configure(Properties props) {
        ConfigurationImpl conf = new ConfigurationImpl();

        // gets the node property
        String node = props.getProperty(NODE_PROP);
        if (node == null || node.trim().equals(""))
            throw new ConfigurationException(format("Invalid '%s' property, either null or empty",
                    NODE_PROP));
        conf.setNode(node.trim());

        // gets the list of adapters
        String[] adapterNames = getStrings(props, ADAPTERS_PROP, ",");
        if (adapterNames.length == 0)
            throw new ConfigurationException("No adapters configured");

        // get the classes of the adapters
        Class<Adapter>[] classes = new Class[adapterNames.length];
        int i = 0;
        for (String name : adapterNames) {
            // full qualified class name
            String adapterName = ADAPTER_PREF_PROP + "." + name;
            String adapterPropPrefix = ADAPTER_PREF_PROP + "." + name + ".";
            String fqcn = props.getProperty(adapterName);
            try {
                classes[i++] = (Class<Adapter>) Class.forName(fqcn);
            } catch (ClassNotFoundException e) {
                throw new ConfigurationException(format("No class qualified as '%s' was found",
                        fqcn), e);
            }

            // sets the adapter specific properties
            Properties adapterProps = new Properties();
            Enumeration<Object> keys = props.keys();
            while (keys.hasMoreElements()) {
                String k = (String) keys.nextElement();
                if (k.startsWith(adapterPropPrefix)) {
                    // if key is: adapter.hbase.pool.size
                    // then subKey will be : pool.size
                    String subKey = k.substring(adapterPropPrefix.length());
                    String value = props.getProperty(k);
                    adapterProps.setProperty(subKey, value);
                }
            }
            conf.setAdapterProperties(name, adapterProps);
        }

        // instances the adapters
        Adapter[] adapters = instancesOf(classes, Adapter.class);
        conf.setAdapters(adapters);

        // initialize adapters
        i = 0;
        for (String name : adapterNames)
            adapters[i++].init(conf.getAdapterProperties(name));

        return conf;
    }
}
