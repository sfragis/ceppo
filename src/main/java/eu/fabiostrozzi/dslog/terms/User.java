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

// User.java, created on Apr 25, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class User implements Term {
    private String username;
    private boolean created = false;
    private boolean deleted = false;
    private boolean signedIn = false;
    private boolean signedOut = false;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the created
     */
    public boolean isCreated() {
        return created;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(boolean created) {
        this.created = created;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the signedIn
     */
    public boolean isSignedIn() {
        return signedIn;
    }

    /**
     * @param signedIn
     *            the signedIn to set
     */
    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    /**
     * @return the signedOut
     */
    public boolean isSignedOut() {
        return signedOut;
    }

    /**
     * @param signedOut
     *            the signedOut to set
     */
    public void setSignedOut(boolean signedOut) {
        this.signedOut = signedOut;
    }
}
