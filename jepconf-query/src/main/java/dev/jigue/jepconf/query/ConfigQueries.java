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
 * Common implementations of {@link ConfigQuery}.
 */
public final class ConfigQueries {
    private ConfigQueries() {
        // No instantiation.
    }

    public static ConfigQuery<Boolean> booleanQuery(final String field) {
        return (config) -> config.getBoolean(field);
    }

    public static ConfigQuery<Long> longQuery(final String field) {
        return (config) -> config.getLong(field);
    }

    public static ConfigQuery<Double> doubleQuery(final String field) {
        return (config) -> config.getDouble(field);
    }

    public static ConfigQuery<String> stringQuery(final String field) {
        return (config) -> config.getString(field);
    }

    public static ConfigQuery<Byte> byteQuery(final String field) {
        return (config) -> config.getByte(field);
    }

    public static ConfigQuery<Short> shortQuery(final String field) {
        return (config) -> config.getShort(field);
    }

    public static ConfigQuery<Integer> intQuery(final String field) {
        return (config) -> config.getInt(field);
    }

    public static ConfigQuery<Float> floatQuery(final String field) {
        return (config) -> config.getFloat(field);
    }

    public static <E extends Enum<E>> ConfigQuery<E> enumQuery(final String field, final Class<E> enumClass) {
        return (config) -> Enum.<E>valueOf(enumClass, config.getString(field));
    }
}
