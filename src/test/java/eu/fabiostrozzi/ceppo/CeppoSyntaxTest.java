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

// CeppoSyntaxTest.java, created on Apr 18, 2012
package eu.fabiostrozzi.ceppo;

import static eu.fabiostrozzi.ceppo.Ceppo.debug;
import static eu.fabiostrozzi.ceppo.Ceppo.error;
import static eu.fabiostrozzi.ceppo.Ceppo.info;
import static eu.fabiostrozzi.ceppo.Ceppo.warn;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import eu.fabiostrozzi.ceppo.Ceppo;
import eu.fabiostrozzi.ceppo.CeppoConfig;

/**
 * Demonstration test.
 * 
 * @author fabio
 */
public class CeppoSyntaxTest {

    @Test
    public void test() throws IOException {
        // run configuration
        CeppoConfig.configure(getClass().getResourceAsStream("/ceppo.properties"));

        final Ceppo log = Ceppo.get(CeppoSyntaxTest.class);
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
                .with("username", "jack", "password", "123***456", "expire_date", new Date())
                .log(log);

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
