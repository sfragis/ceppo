// Utils.java, created on Apr 24, 2012
package eu.fabiostrozzi.dslog;

import static java.lang.String.format;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/**
 * Common utilities.
 * 
 * @author fabio
 */
public class Utils {

    /**
     * Instances a an array of classes.
     * 
     * @param classes
     * @return
     */
    public static <T> T[] instancesOf(Class<T>[] classes, Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(clazz, classes.length);

        int i = 0;
        for (Class<T> c : classes) {
            try {
                Constructor<T> con = c.getConstructor();
                T t = con.newInstance();
                array[i++] = t;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(format(
                        "Class '%s' does not have a zero-arity constructor", c.getCanonicalName()),
                        e);
            } catch (Exception e) {
                throw new RuntimeException(format("Cannot instance object of type '%s'",
                        c.getCanonicalName()), e);
            }
        }
        return array;
    }

    /**
     * @param list
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <S, T extends S> T lastOrAppendNew(ArrayList<S> list, Class<T> clazz) {
        S s = list.get(list.size() - 1);
        T t = null;
        if (clazz.isInstance(s)) {
            t = (T) s;
        } else {
            try {
                t = clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Cannot instance object of type '"
                        + clazz.getCanonicalName() + "'", e);
            }
            list.add(t);
        }
        return t;
    }

    /**
     * Gets an array of string properties given a key and a proper word separator.
     * 
     * @param props
     *            The properties
     * @param key
     *            The key
     * @param sep
     *            The words separator
     * @return An array of string properties
     */
    public static String[] getStrings(Properties props, String key, String sep) {
        String val = props.getProperty(key);
        if (val == null || val.trim().equals(""))
            return new String[0];

        ArrayList<String> l = new ArrayList<String>(5);
        Scanner s = new Scanner(val);
        s.useDelimiter(sep);
        while (s.hasNext()) {
            String v = s.next();
            if (v != null && !v.trim().equals(""))
                l.add(v.trim());
        }
        return l.toArray(new String[0]);
    }

    /**
     * @param content
     * @param separator
     * @return
     */
    public static Object joinLines(String content, String separator) {
        if (content == null)
            return null;
        content = content.replaceAll("\\r\\n|\\r|\\n", separator);
//        content = content.replaceAll("\r\n", separator).replaceAll("\n", separator);
        return content;
    }
}
