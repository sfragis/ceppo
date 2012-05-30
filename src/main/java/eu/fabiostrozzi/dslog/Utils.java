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
        for (Class<T> c : classes)
            array[i++] = instanceOf(c);
        return array;
    }

    /**
     * Instances a new class.
     * 
     * @param clazz
     * @return
     */
    public static <T> T instanceOf(Class<T> clazz) {
        try {
            Constructor<T> con = clazz.getConstructor();
            T t = con.newInstance();
            return t;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(format("Class '%s' does not have a zero-arity constructor",
                    clazz.getCanonicalName()), e);
        } catch (Exception e) {
            throw new RuntimeException(format("Cannot instance object of type '%s'",
                    clazz.getCanonicalName()), e);
        }
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
     * @param content
     * @param separator
     * @return
     */
    public static String joinLines(String content, String separator) {
        if (content == null)
            return null;
        content = content.replaceAll("\\r\\n|\\r|\\n", separator);
        // content = content.replaceAll("\r\n", separator).replaceAll("\n", separator);
        return content;
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
     * Gets a string property value given a property key and a default, fallback value.
     * 
     * @param props
     *            The set of properties
     * @param key
     *            The property key
     * @param dflt
     *            The default value, used as a fallback value in case of exceptions.
     * @return The string value associated to the specified property.
     */
    public static String getString(Properties props, String key, String dflt) {
        String val = props.getProperty(key);
        return (val == null || val.trim().equals("")) ? dflt : val.trim();
    }

    /**
     * Gets an integer property value given a property key and a default, fallback value.
     * 
     * @param props
     *            The set of properties
     * @param key
     *            The property key
     * @param dflt
     *            The default value, used as a fallback value in case of exceptions.
     * @return The integer value associated to the specified property.
     */
    public static int getInt(Properties props, String key, int dflt) {
        String sv = props.getProperty(key);
        if (sv == null || sv.trim().equals(""))
            return dflt;

        int iv = dflt;
        try {
            iv = Integer.parseInt(sv.trim());
        } catch (NumberFormatException e) {
            // TODO
        }
        return iv;
    }
}
