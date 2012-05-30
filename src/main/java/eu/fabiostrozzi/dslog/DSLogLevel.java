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

// DSLogLevel.java, created on Apr 22, 2012
package eu.fabiostrozzi.dslog;

import java.util.Hashtable;

/**
 * @author fabio
 * 
 */
public enum DSLogLevel {
    DEBUG(0), INFO(1), WARNING(2), ERROR(3), FATAL(4);

    private static final Hashtable<String, DSLogLevel> strmap = new Hashtable<String, DSLogLevel>();
    private static final DSLogLevel[] intmap = new DSLogLevel[values().length];

    static {
        for (DSLogLevel l : values()) {
            strmap.put(l.name().toLowerCase(), l);
            intmap[l.level] = l;
        }
    }

    private int level;

    private DSLogLevel(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }

    public boolean isGreatEqual(DSLogLevel that) {
        return level >= that.level;
    }

    public boolean isLowerEqual(DSLogLevel that) {
        return level <= that.level;
    }

    public static DSLogLevel of(String name) {
        return strmap.get(name.toLowerCase());
    }

    public static DSLogLevel of(int level) {
        return level < intmap.length ? intmap[level] : null;
    }
}