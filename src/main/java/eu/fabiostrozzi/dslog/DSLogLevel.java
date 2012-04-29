// DSLogLevel.java, created on Apr 22, 2012
package eu.fabiostrozzi.dslog;

/**
 * @author fabio
 * 
 */
public enum DSLogLevel {
    FATAL(4), ERROR(3), WARNING(2), INFO(1), DEBUG(0);

    private int level;

    private DSLogLevel(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }
}