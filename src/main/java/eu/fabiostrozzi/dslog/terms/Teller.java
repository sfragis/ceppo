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

// Teller.java, created on Apr 20, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class Teller implements Term {
	private String when;
	private Object[] whenParams;

	/**
	 * @return the when
	 */
	public String getWhen() {
		return when;
	}

	/**
	 * @param when
	 *            the when to set
	 */
	public void setWhen(String when) {
		this.when = when;
	}

	/**
	 * @return the whenParams
	 */
	public Object[] getWhenParams() {
		return whenParams;
	}

	/**
	 * @param whenParams
	 *            the whenParams to set
	 */
	public void setWhenParams(Object[] whenParams) {
		this.whenParams = whenParams;
	}
}
