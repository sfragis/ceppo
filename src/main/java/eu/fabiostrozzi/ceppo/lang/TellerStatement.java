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

// TellerStatement.java, created on Apr 18, 2012
package eu.fabiostrozzi.ceppo.lang;

/**
 * @author fabio
 * 
 */
public interface TellerStatement extends SubjectStatement {
	/**
	 * @param when
	 * @return
	 */
	SubjectStatement when(String when);

	/**
	 * @param format
	 * @param params
	 * @return
	 */
	SubjectStatement when(String format, Object[] params);

	/**
	 * @param when
	 * @return
	 */
	SubjectStatement during(String when);

	/**
	 * @param format
	 * @param params
	 * @return
	 */
	SubjectStatement during(String format, Object[] params);
}