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

// Happening.java, created on Apr 19, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class Happening implements Term {
    private String what;
    private Throwable exception;
    private Object[] params;

    /**
     * @return the what
     */
    public String getWhat() {
        return what;
    }

    /**
     * @param what
     *            the what to set
     */
    public void setWhat(String what) {
        this.what = what;
    }

    /**
     * @return the exception
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * @param exception
     *            the exception to set
     */
    public void setException(Throwable throwable) {
        this.exception = throwable;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(Object[] params) {
        this.params = params;
    }
}
