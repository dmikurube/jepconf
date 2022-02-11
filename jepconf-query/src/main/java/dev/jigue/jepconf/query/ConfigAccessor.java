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

import static dev.jigue.jepconf.query.Types.nick;

import dev.jigue.jepconf.ConfigMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ConfigAccessor {
    private ConfigAccessor(final ConfigMap configMap) {
        this.configMap = configMap;
    }

    /**
     * Creates a {@link ConfigAccessor} instance from {@link java.util.Map}.
     *
     * @throws IllegalArgumentException  if ...
     */
    public static ConfigAccessor onSnapshotOf(final Map<String, Object> configMap) {
        return new ConfigAccessor(ConfigMap.snapshotOf(configMap));
    }

    public Optional<Object> getObjectOptional(final String field) {
        Objects.requireNonNull(field, "The field name specified is null.");
        return Optional.ofNullable(this.configMap.get(field));
    }

    public Object getObject(final String field) {
        return this.getObjectOptional(field).orElseThrow(() ->
            new NoSuchElementException("The field \"" + field + "\" does not exist.")
        );
    }

    public boolean getBoolean(final String field) {
        return castToBoolean(this.getObject(field), field).booleanValue();
    }

    public boolean getBooleanOrDefault(final String field, final boolean defaultValue) {
        return this.getObjectOptional(field).map(object -> {
            return castToBoolean(this.getObject(field), field);
        }).orElse(Boolean.valueOf(defaultValue)).booleanValue();
    }

    public long getLong(final String field) {
        return castToLong(this.getObject(field), field).longValue();
    }

    public long getLongOrDefault(final String field, final long defaultValue) {
        return this.getObjectOptional(field).map(object -> {
            return castToLong(this.getObject(field), field);
        }).orElse(Long.valueOf(defaultValue)).longValue();
    }

    public double getDouble(final String field) {
        return castToDouble(this.getObject(field), field).doubleValue();
    }

    public double getDoubleOrDefault(final String field, final double defaultValue) {
        return this.getObjectOptional(field).map(object -> {
            return castToDouble(this.getObject(field), field);
        }).orElse(Double.valueOf(defaultValue)).doubleValue();
    }

    public String getString(final String field) {
        return castToString(this.getObject(field), field);
    }

    public String getStringOrDefault(final String field, final String defaultValue) {
        return this.getObjectOptional(field).map(object -> {
            return castToString(this.getObject(field), field);
        }).orElse(String.valueOf(defaultValue));
    }

    public <T> List<T> getList(final String field, final Class<T> clazz) {
        final Object object = this.getObject(field);
        if (!(object instanceof List)) {
            throw new ClassCastException("The field \"" + field + "\" is not List, but <" + nick(object.getClass()) + ">.");
        }
        final LinkedHashSet<Class<?>> invalidClasses = new LinkedHashSet<>();
        for (final Object element : (List<?>) object) {
            final Class<?> elementClass = element.getClass();
            if (!clazz.isAssignableFrom(elementClass)) {
                invalidClasses.add(elementClass);
            }
        }
        if (invalidClasses.isEmpty()) {
            return castListT((List<?>) object, clazz);
        }
        throw new ClassCastException(
                "The field \"" + field + "\" is List, expected to contain " + nick(clazz)
                    + ", but contains "
                    + invalidClasses.stream().map(c -> nick(c)).collect(Collectors.joining(", ", "[", "].")));
    }

    public <T> Map<String, T> getMap(final String field, final Class<T> clazz) {
        final Object object = this.getObject(field);
        if (!(object instanceof Map)) {
            throw new ClassCastException("The field \"" + field + "\" is not Map, but <" + nick(object.getClass()) + ">.");
        }
        final LinkedHashSet<Class<?>> invalidKeyClasses = new LinkedHashSet<>();
        final LinkedHashSet<Class<?>> invalidValueClasses = new LinkedHashSet<>();
        for (final Map.Entry entry : ((Map<?, ?>) object).entrySet()) {
            final Object key = entry.getKey();
            if (!(key instanceof String)) {
                invalidKeyClasses.add(key.getClass());
            }
            final Class<?> valueClass = entry.getValue().getClass();
            if (!clazz.isAssignableFrom(valueClass)) {
                invalidValueClasses.add(valueClass);
            }
        }
        if (invalidKeyClasses.isEmpty() && invalidValueClasses.isEmpty()) {
            return castMapStringT((Map) object, clazz);
        }

        final StringBuilder message = new StringBuilder();
        message.append("The field \"").append(field);
        message.append("\" is Map, expected to contain String keys and ").append(nick(clazz)).append(" values, but contains");
        if (!invalidKeyClasses.isEmpty()) {
            message.append(" key(s) of [")
                    .append(invalidKeyClasses.stream().map(c -> nick(c)).collect(Collectors.joining(", ")))
                    .append("]");
            if (!invalidValueClasses.isEmpty()) {
                message.append(", and");
            }
        }
        if (!invalidValueClasses.isEmpty()) {
            message.append(" value(s) of [")
                    .append(invalidValueClasses.stream().map(c -> nick(c)).collect(Collectors.joining(", ")))
                    .append("]");
        }
        message.append(".");
        throw new ClassCastException(message.toString());
    }

    public byte getByte(final String field) {
        return (byte) narrowLong(this.getLong(field), Byte.MIN_VALUE, Byte.MAX_VALUE, field, "byte");
    }

    public byte getByteOrDefault(final String field, final byte defaultValue) {
        return (byte) narrowLong(this.getLongOrDefault(field, defaultValue), Byte.MIN_VALUE, Byte.MAX_VALUE, field, "byte");
    }

    public short getShort(final String field) {
        return (short) narrowLong(this.getLong(field), Short.MIN_VALUE, Short.MAX_VALUE, field, "short");
    }

    public short getShortOrDefault(final String field, final short defaultValue) {
        return (short) narrowLong(this.getLongOrDefault(field, defaultValue), Short.MIN_VALUE, Short.MAX_VALUE, field, "short");
    }

    public int getInt(final String field) {
        return (int) narrowLong(this.getLong(field), Integer.MIN_VALUE, Integer.MAX_VALUE, field, "int");
    }

    public int getIntOrDefault(final String field, final int defaultValue) {
        return (int) narrowLong(this.getLongOrDefault(field, defaultValue), Integer.MIN_VALUE, Integer.MAX_VALUE, field, "int");
    }

    public float getFloat(final String field) {
        return (float) narrowDouble(this.getDouble(field), Float.MIN_VALUE, Float.MAX_VALUE, field, "float");
    }

    public float getFloatOrDefault(final String field, final float defaultValue) {
        return (float) narrowDouble(this.getDoubleOrDefault(field, defaultValue), Float.MIN_VALUE, Float.MAX_VALUE, field, "float");
    }

    public <R> R query(final ConfigQuery<R> query) {
        return query.queryFrom(this);
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof ConfigAccessor)) {
            return false;
        }
        final ConfigAccessor other = (ConfigAccessor) otherObject;
        return Objects.equals(this.configMap, other.configMap);

    }

    @Override
    public int hashCode() {
        return Objects.hash(ConfigAccessor.class, this.configMap);
    }

    private static <T> T castObject(final Object object, final Class<T> clazz, final String field) {
        if (!clazz.isAssignableFrom(object.getClass())) {
            throw new ClassCastException(
                    "The field \"" + field + "\" is not " + nick(clazz) + ", but <" + nick(object.getClass()) + ">.");
        }
        return clazz.cast(object);
    }

    private static Boolean castToBoolean(final Object object, final String field) {
        return castObject(object, Boolean.class, field);
    }

    private static Long castToLong(final Object object, final String field) {
        return castObject(object, Long.class, field);
    }

    private static Double castToDouble(final Object object, final String field) {
        return castObject(object, Double.class, field);
    }

    private static String castToString(final Object object, final String field) {
        return castObject(object, String.class, field);
    }

    private static long narrowLong(final long value, final long min, final long max, final String field, final String type) {
        if (min <= value && value <= max) {
            return value;
        }
        throw new ArithmeticException("The field \"" + field + "\" is out of " + type + " range.");
    }

    private static double narrowDouble(final double value, final double min, final double max, final String field, final String type) {
        if (min <= value && value <= max) {
            return value;
        }
        throw new ArithmeticException("The field \"" + field + "\" is out of " + type + " range.");
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> castListT(final List<?> list, final Class<T> clazz) {
        return (List<T>) list;
    }

    @SuppressWarnings("unchecked")
    private static <T> Map<String, T> castMapStringT(final Map<?, ?> map, final Class<T> clazz) {
        return (Map<String, T>) map;
    }

    private final ConfigMap configMap;
}
