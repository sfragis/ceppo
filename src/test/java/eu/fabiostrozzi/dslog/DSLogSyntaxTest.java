// DSLogSyntaxTest.java, created on Apr 18, 2012
package eu.fabiostrozzi.dslog;

import static eu.fabiostrozzi.dslog.DSLog.debug;
import static eu.fabiostrozzi.dslog.DSLog.error;
import static eu.fabiostrozzi.dslog.DSLog.info;
import static eu.fabiostrozzi.dslog.DSLog.warn;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

/**
 * Demonstration test.
 * 
 * @author fabio
 */
public class DSLogSyntaxTest {

    @Test
    public void test() throws IOException {
        // run configuration
        DSLogConfig.configure(getClass().getResourceAsStream("/dslog.properties"));

        final DSLog log = DSLog.get(DSLogSyntaxTest.class);
        log.setUp();

        // use message to tell something about a message being sent or received
        info().message("<root>xxx</root>").from("system A").succeeded().log(log);

        // use when or during to specify the context
        error().when("order loading").message("<root>yyy</root>").to("system B")
                .failed("broken pipe").log(log);
        error().during("order fulfilment").message("<root>yyy</root>").to("system B")
                .failed("broken pipe").log(log);

        info().message("activation").content("<root>zzz</root>").to("system C").prepared().log(log);

        // use constraint to tell something about any constraint
        warn().constraint("minimum level").violated("input level is too low").log(log);
        debug().when("creating user").constraint("user does not exist")
                .passed("no users found with the same login name").log(log);

        // something about a user
        info().user("jack").created()
                .with("username", "jack", "password", "123***456", "expire_date", new Date()).log(log);

        String username = "pippo";
        int id = 101;
        info().entity("subscription").did("created").with("id", id, "username", username).log(log);

        try {
            throw new RuntimeException("pippo pluto paperino");
        } catch (Throwable t) {
            // yet unexcepted exception can still be logged
            error().when("saving user").occurred(t).log(log);
        }
    }

}
