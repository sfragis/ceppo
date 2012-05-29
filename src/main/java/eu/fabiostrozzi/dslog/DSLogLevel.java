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