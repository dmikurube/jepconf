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

package dev.jigue.jepconf;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An immutable {@link java.util.Map} implementation to represent configuration data based on associative arrays such as
 * JSON, YAML, TOML, and so on.
 *
 * <p>This implementation is immutable. It is guaranteed to contain only {@link Boolean}, {@link Long}, {@link Double},
 * {@link String}, {@link dev.jigue.jepconf.ConfigList}, and {@link dev.jigue.jepconf.ConfigMap} once it is instantiated.
 *
 * <h2><a id="valid-keys">Valid Keys</a></h2>
 * <p>A {@code null} is considered invalid as a {@link java.util.Map} key.
 *
 * <h2><a id="valid-values">Valid Values</a></h2>
 * <p>A {@code null} is considered valid as a {@link java.util.Map} value.
 *
 * @see <a href="https://www.json.org/">JSON</a>
 * @see <a href="https://yaml.org/">YAML</a>
 * @see <a href="https://toml.io/">TOML</a>
 */
public final class ConfigMap extends AbstractMap<String, Object> {
    private ConfigMap(final LinkedHashMap<String, Object> verifiedMap, final boolean isPrivate) {
        this.inner = Collections.unmodifiableMap(verifiedMap);
    }

    /**
     * Constructs an empty {@link dev.jigue.jepconf.ConfigMap}.
     *
     * @deprecated It works here to help frameworks that expect constructors to call automatically, such as DI containers.
     *     The constructor is not for removal, but it is discouraged to use. Use {@link #of()} instead.
     */
    @Deprecated
    public ConfigMap() {
        this(new LinkedHashMap<>(), true);
    }

    /**
     * Constructs a {@link dev.jigue.jepconf.ConfigMap} with normalized and deep-copied mappings of the specified {@link java.util.Map}.
     *
     * @param map  the {@link java.util.Map} whose normalized and deep-copied mappings are to be placed in this {@link dev.jigue.jepconf.ConfigMap}, not null
     * @throws IllegalArgumentException  if the specified {@link java.util.Map} contains an invalid key or value for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the specified {@link java.util.Map} is {@code null}
     * @deprecated It works here to help frameworks that expect constructors to call automatically, such as DI containers.
     *     The constructor is not for removal, but it is discouraged to use. Use {@link #snapshotOf(java.util.Map)} instead.
     */
    @Deprecated
    public ConfigMap(final Map<String, ?> map) {
        this(normalizeForInnerMap(Objects.requireNonNull(map, "The specified map is null."), false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing zero mappings.
     *
     * @return an empty {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigMap of() {
        return new ConfigMap(new LinkedHashMap<>(), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing a normalized deep copy of the specified single mapping.
     *
     * @param k1  the mapping's key, not null
     * @param v1  the mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing a normalized deep copy of the specified mapping
     * @throws IllegalArgumentException  if the specified key or value is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified two mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified three mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified four mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key, not null
     * @param v4 the fourth mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3,
            final String k4, final Object v4) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        putSafe(inner, k4, v4);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified five mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key, not null
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key, not null
     * @param v5 the fifth mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3,
            final String k4, final Object v4,
            final String k5, final Object v5) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        putSafe(inner, k4, v4);
        putSafe(inner, k5, v5);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified six mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key, not null
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key, not null
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key, not null
     * @param v6 the sixth mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3,
            final String k4, final Object v4,
            final String k5, final Object v5,
            final String k6, final Object v6) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        putSafe(inner, k4, v4);
        putSafe(inner, k5, v5);
        putSafe(inner, k6, v6);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified seven mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key, not null
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key, not null
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key, not null
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key, not null
     * @param v7 the seventh mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3,
            final String k4, final Object v4,
            final String k5, final Object v5,
            final String k6, final Object v6,
            final String k7, final Object v7) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        putSafe(inner, k4, v4);
        putSafe(inner, k5, v5);
        putSafe(inner, k6, v6);
        putSafe(inner, k7, v7);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified eight mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key, not null
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key, not null
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key, not null
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key, not null
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key, not null
     * @param v8 the eighth mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3,
            final String k4, final Object v4,
            final String k5, final Object v5,
            final String k6, final Object v6,
            final String k7, final Object v7,
            final String k8, final Object v8) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        putSafe(inner, k4, v4);
        putSafe(inner, k5, v5);
        putSafe(inner, k6, v6);
        putSafe(inner, k7, v7);
        putSafe(inner, k8, v8);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified nine mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key, not null
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key, not null
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key, not null
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key, not null
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key, not null
     * @param v8 the eighth mapping's value
     * @param k9 the ninth mapping's key, not null
     * @param v9 the ninth mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3,
            final String k4, final Object v4,
            final String k5, final Object v5,
            final String k6, final Object v6,
            final String k7, final Object v7,
            final String k8, final Object v8,
            final String k9, final Object v9) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        putSafe(inner, k4, v4);
        putSafe(inner, k5, v5);
        putSafe(inner, k6, v6);
        putSafe(inner, k7, v7);
        putSafe(inner, k8, v8);
        putSafe(inner, k9, v9);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified ten mappings.
     *
     * @param k1 the first mapping's key, not null
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key, not null
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key, not null
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key, not null
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key, not null
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key, not null
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key, not null
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key, not null
     * @param v8 the eighth mapping's value
     * @param k9 the ninth mapping's key, not null
     * @param v9 the ninth mapping's value
     * @param k10 the tenth mapping's key, not null
     * @param v10 the tenth mapping's value
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if the keys are duplicates, or if at least one of the specified keys and values are
     *     invalid for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if any key is {@code null}
     */
    public static ConfigMap of(
            final String k1, final Object v1,
            final String k2, final Object v2,
            final String k3, final Object v3,
            final String k4, final Object v4,
            final String k5, final Object v5,
            final String k6, final Object v6,
            final String k7, final Object v7,
            final String k8, final Object v8,
            final String k9, final Object v9,
            final String k10, final Object v10) {
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        putSafe(inner, k1, v1);
        putSafe(inner, k2, v2);
        putSafe(inner, k3, v3);
        putSafe(inner, k4, v4);
        putSafe(inner, k5, v5);
        putSafe(inner, k6, v6);
        putSafe(inner, k7, v7);
        putSafe(inner, k8, v8);
        putSafe(inner, k9, v9);
        putSafe(inner, k10, v10);
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} containing normalized and deep-copied keys and values extracted from the given entries.
     *
     * @param entries  {@link java.util.Map.Entry}s containing normalized and deep-copied keys and values from which the map is populated, not null
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mappings
     * @throws IllegalArgumentException  if there are any duplicate keys, or if at least one of the specified entries contain
     *     an invalid key or value for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the array or any key is {@code null}
     */
    @SafeVarargs
    @SuppressWarnings({"varargs"})
    public static ConfigMap ofEntries(final Map.Entry<String, ?>... entries) {
        Objects.requireNonNull(entries, "The specified array is null.");
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        for (final Map.Entry<String, ?> entry : entries) {
            putSafe(inner, entry.getKey(), (Object) entry.getValue());
        }
        return new ConfigMap(normalizeForInnerMap(inner, false), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigMap} with normalized and deep-copied mappings of the specified {@link java.util.Map}.
     *
     * @param map  the {@link java.util.Map} whose normalized and deep-copied mappings are to be placed in this {@link dev.jigue.jepconf.ConfigMap}, not null
     * @return a {@link dev.jigue.jepconf.ConfigMap} containing normalized deep copies of the specified mapping
     * @throws IllegalArgumentException  if the specified {@link java.util.Map} contains at least one invalid key or value
     *     for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the specified {@link java.util.Map} is {@code null}
     */
    public static ConfigMap snapshotOf(final Map<String, ?> map) {
        Objects.requireNonNull(map, "The specified map is null.");
        if (map instanceof ConfigMap) {
            return castAnyMapToConfigMap(map);
        }
        return new ConfigMap(normalizeForInnerMap(map, false), true);
    }

    /**
     * Returns a new mutable {@link java.util.LinkedHashMap} with normalized and deep-copied mappings of the specified {@link java.util.Map}.
     *
     * <p>Although it returns a mutable {@link java.util.LinkedHashMap}, it applies the same validation with {@link dev.jigue.jepconf.ConfigMap}.
     *
     * @param map  the {@link java.util.Map} whose normalized and deep-copied mappings are to be placed in this {@link java.util.LinkedHashMap}, not null
     * @return a {@link java.util.LinkedHashMap} containing normalized deep copies of the specified mapping
     * @throws IllegalArgumentException  if the specified {@link java.util.Map} contains at least one invalid key or value
     *     for {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the specified {@link java.util.Map} is {@code null}
     */
    public static LinkedHashMap<String, Object> mutableSnapshotOf(final Map<String, ?> map) {
        Objects.requireNonNull(map, "The specified map is null.");
        return normalizeForInnerMap(map, true);
    }

    @SuppressWarnings("unchecked")
    static ConfigMap snapshotOfAnyMap(final Map<?, ?> map) {
        return snapshotOf((Map<String, ?>) map);
    }

    @SuppressWarnings("unchecked")
    static LinkedHashMap<String, Object> mutableSnapshotOfAnyMap(final Map<?, ?> map) {
        return mutableSnapshotOf((Map<String, ?>) map);
    }

    /**
     * Returns a {@link java.util.Set} view of the mappings contained in this {@link dev.jigue.jepconf.ConfigMap}.
     *
     * @return a set view of the mappings contained in this {@link dev.jigue.jepconf.ConfigMap}
     */
    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.inner.entrySet();
    }

    @SuppressWarnings("unchecked")
    private static ConfigMap castAnyMapToConfigMap(final Map<?, ?> map) {
        return (ConfigMap) map;
    }

    private static LinkedHashMap<String, Object> normalizeForInnerMap(final Map<String, ?> map, final boolean mutable) {
        assertForInnerMap(map);
        final LinkedHashMap<String, Object> inner = new LinkedHashMap<>();
        for (final Map.Entry<String, ?> entry : map.entrySet()) {
            inner.put(entry.getKey(), Types.normalizeObject(entry.getValue(), mutable));
        }
        return inner;
    }

    private static void assertForInnerMap(final Map<?, ?> map) {
        try {
            Types.assertAnyMapIsValidForConfig(map);
        } catch (final InvalidKeysException ex) {  // Invalid keys are prioritized over invalid values.
            throw new IllegalArgumentException(ex.buildExceptionMessage());
        } catch (final InvalidValuesException ex) {
            throw new IllegalArgumentException(ex.buildExceptionMessage());
        }
    }

    private static void putSafe(final LinkedHashMap<String, Object> map, final String key, final Object value) {
        Objects.requireNonNull(key, "A key is null.");
        if (map.containsKey(key)) {
            throw new IllegalArgumentException("A key is duplicated: " + key);
        }
        map.put(key, value);
    }

    private final Map<String, Object> inner;
}
