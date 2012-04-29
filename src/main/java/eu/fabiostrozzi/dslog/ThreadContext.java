// ThreadContext.java, created on Apr 22, 2012
package eu.fabiostrozzi.dslog;

import java.util.UUID;

public class ThreadContext {
    private UUID uuid;

    /**
     * @param uuid
     */
    public ThreadContext() {
        this.uuid = UUID.randomUUID();
    }

    /**
     * @return
     */
    public UUID getUUID() {
        return uuid;
    }
}