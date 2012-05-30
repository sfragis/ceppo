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

// CeppoLevel.java, created on Apr 22, 2012
package eu.fabiostrozzi.ceppo;

import java.util.Hashtable;

/**
 * @author fabio
 * 
 */
public enum CeppoLevel {
    DEBUG(0), INFO(1), WARNING(2), ERROR(3), FATAL(4);

    private static final Hashtable<String, CeppoLevel> strmap = new Hashtable<String, CeppoLevel>();
    private static final CeppoLevel[] intmap = new CeppoLevel[values().length];

    static {
        for (CeppoLevel l : values()) {
            strmap.put(l.name().toLowerCase(), l);
            intmap[l.level] = l;
        }
    }

    private int level;

    private CeppoLevel(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }

    public boolean isGreatEqual(CeppoLevel that) {
        return level >= that.level;
    }

    public boolean isLowerEqual(CeppoLevel that) {
        return level <= that.level;
    }

    public static CeppoLevel of(String name) {
        return strmap.get(name.toLowerCase());
    }

    public static CeppoLevel of(int level) {
        return level < intmap.length ? intmap[level] : null;
    }
}