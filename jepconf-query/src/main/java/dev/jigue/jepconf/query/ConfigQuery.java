/*
 * Copyright 2022 Dai MIKURUBE
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

package dev.jigue.jepconf.query;

/**
 * Strategy for querying a configuration accessor object that wraps {@link java.util.Map}.
 *
 * @param <R> the type returned from the query
 */
@FunctionalInterface
public interface ConfigQuery<R> {
    /**
     * Queries the specified configuration accessor object.
     *
     * @implSpec
     * The implementation must take the input configuration accessor object, and query it.
     *
     * @param config  the configuration accessor object to query, not {@code null}
     * @return the queried value, may return {@code null} to indicate not found
     */
    R queryFrom(final ConfigAccessor config);
}
