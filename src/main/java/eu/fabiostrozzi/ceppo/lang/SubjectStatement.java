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

// SubjectStatement.java, created on Apr 18, 2012
package eu.fabiostrozzi.ceppo.lang;

/**
 * @author fabio
 * 
 */
public interface SubjectStatement {
    /**
     * A message identified by a message code.
     * 
     * @param msg
     * @return
     */
    MessageStatement message(String msg);

    /**
     * A user identified by a username.
     * 
     * @param username
     * @return
     */
    UserStatement user(String username);

    /**
     * @param constraint
     * @return
     */
    ConstraintStatement constraint(String constraint);

    /**
     * @param what
     * @return
     */
    ClosureStatement occurred(String what);

    /**
     * @param what
     * @param values
     * @return
     */
    ClosureStatement occurred(String what, Object... values);

    /**
     * @param throwable
     * @return
     */
    ClosureStatement occurred(Throwable throwable);

    /**
     * @param entity
     * @return
     */
    EntityStatement entity(String entity);
}
