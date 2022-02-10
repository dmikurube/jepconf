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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An immutable {@link java.util.List} implementation to represent configuration data based on associative arrays such as
 * JSON, YAML, TOML, and so on.
 *
 * <p>This implementation is immutable. It is guaranteed to contain only {@link Boolean}, {@link Long}, {@link Double},
 * {@link String}, {@link dev.jigue.jepconf.ConfigList}, and {@link dev.jigue.jepconf.ConfigMap} once it is instantiated.
 *
 * @see <a href="https://www.json.org/">JSON</a>
 * @see <a href="https://yaml.org/">YAML</a>
 * @see <a href="https://toml.io/">TOML</a>
 */
public final class ConfigList extends AbstractList<Object> {
    private ConfigList(final ArrayList<Object> verifiedList, final boolean isPrivate) {
        this.inner = Collections.unmodifiableList(verifiedList);
    }

    /**
     * Constructs an empty {@link dev.jigue.jepconf.ConfigList}.
     *
     * @deprecated It works here to help frameworks that expect constructors to call automatically, such as DI containers.
     *     The constructor is not for removal, but it is discouraged to use. Use {@link #of()} instead.
     */
    @Deprecated
    public ConfigList() {
        this(new ArrayList<>(), true);
    }

    /**
     * Constructs a new {@link dev.jigue.jepconf.ConfigList} containing the elements of the specified {@link java.util.List}.
     *
     * @param list  the {@link java.util.List} whose elements are to be placed into this {@link dev.jigue.jepconf.ConfigList}, not null
     * @throws IllegalArgumentException  if the specified {@link java.util.List} contains an invalid element for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the specified {@link java.util.List} is {@code null}
     * @deprecated It works here to help frameworks that expect constructors to call automatically, such as DI containers.
     *     The constructor is not for removal, but it is discouraged to use. Use {@link #snapshotOf(java.util.List)} instead.
     */
    @Deprecated
    public ConfigList(final List<?> list) {
        this(normalizeForInnerList(Objects.requireNonNull(list, "The specified list is null.")), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing zero elements.
     *
     * @return an empty {@link dev.jigue.jepconf.ConfigList}
     */
    public static ConfigList of() {
        return new ConfigList(new ArrayList<>(), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing one element.
     *
     * @param e1 the single element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified element
     * @throws IllegalArgumentException  if the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing two elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing three elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing four elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3,
            final Object e4) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        inner.add(e4);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing five elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3,
            final Object e4,
            final Object e5) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        inner.add(e4);
        inner.add(e5);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing six elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3,
            final Object e4,
            final Object e5,
            final Object e6) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        inner.add(e4);
        inner.add(e5);
        inner.add(e6);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing seven elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3,
            final Object e4,
            final Object e5,
            final Object e6,
            final Object e7) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        inner.add(e4);
        inner.add(e5);
        inner.add(e6);
        inner.add(e7);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing eight elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3,
            final Object e4,
            final Object e5,
            final Object e6,
            final Object e7,
            final Object e8) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        inner.add(e4);
        inner.add(e5);
        inner.add(e6);
        inner.add(e7);
        inner.add(e8);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing nine elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @param e9 the ninth element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3,
            final Object e4,
            final Object e5,
            final Object e6,
            final Object e7,
            final Object e8,
            final Object e9) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        inner.add(e4);
        inner.add(e5);
        inner.add(e6);
        inner.add(e7);
        inner.add(e8);
        inner.add(e9);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing ten elements.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @param e9 the ninth element
     * @param e10 the tenth element
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     */
    public static ConfigList of(
            final Object e1,
            final Object e2,
            final Object e3,
            final Object e4,
            final Object e5,
            final Object e6,
            final Object e7,
            final Object e8,
            final Object e9,
            final Object e10) {
        final ArrayList<Object> inner = new ArrayList<>();
        inner.add(e1);
        inner.add(e2);
        inner.add(e3);
        inner.add(e4);
        inner.add(e5);
        inner.add(e6);
        inner.add(e7);
        inner.add(e8);
        inner.add(e9);
        inner.add(e10);
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a {@link dev.jigue.jepconf.ConfigList} containing an arbitrary number of elements.
     *
     * @param elements  the elements to be contained in the {@link dev.jigue.jepconf.ConfigList}, not null
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if at least one of the specified {@link java.lang.Object} is invalid for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the array is {@code null}
     */
    public static ConfigList of(final Object... elements) {
        Objects.requireNonNull(elements, "The specified array is null.");
        final ArrayList<Object> inner = new ArrayList<>();
        for (final Object element : elements) {
            inner.add(element);
        }
        return new ConfigList(normalizeForInnerList(inner), true);
    }

    /**
     * Returns a new {@link dev.jigue.jepconf.ConfigList} containing the elements of the specified {@link java.util.List}.
     *
     * @param list  the {@link java.util.List} whose elements are to be placed into this {@link dev.jigue.jepconf.ConfigList}, not null
     * @return a {@link dev.jigue.jepconf.ConfigList} containing the specified elements
     * @throws IllegalArgumentException  if the specified {@link java.util.List} contains an invalid element for
     *     {@link dev.jigue.jepconf.ConfigList} and {@link dev.jigue.jepconf.ConfigMap}
     * @throws NullPointerException  if the specified {@link java.util.List} is {@code null}
     */
    public static ConfigList snapshotOf(final List<?> list) {
        Objects.requireNonNull(list, "The specified list is null.");
        if (list instanceof ConfigList) {
            return castAnyListToConfigList(list);
        }
        return new ConfigList(normalizeForInnerList(list), true);
    }

    /**
     * Returns the number of elements in this {@link dev.jigue.jepconf.ConfigList}.
     *
     * <p>If this {@link dev.jigue.jepconf.ConfigList} contains more than {@code Integer.MAX_VALUE} elements,
     * returns {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this {@link dev.jigue.jepconf.ConfigList}
     */
    @Override
    public int size() {
        return this.inner.size();
    }

    /**
     * Returns the element at the specified position in this {@link dev.jigue.jepconf.ConfigList}.
     *
     * @param index  index of the element to return
     * @return the element at the specified position in this {@link dev.jigue.jepconf.ConfigList}
     * @throws IndexOutOfBoundsException  if the index is out of range ({@code index < 0 || index >= size()})
     */
    @Override
    public Object get(final int index) {
        return this.inner.get(index);
    }

    @SuppressWarnings("unchecked")
    private static ConfigList castAnyListToConfigList(final List<?> list) {
        return (ConfigList) list;
    }

    private static ArrayList<Object> normalizeForInnerList(final List<?> list) {
        assertForInnerList(list);
        final ArrayList<Object> inner = new ArrayList<>();
        for (final Object element : list) {
            inner.add(Types.normalizeObject(element));
        }
        return inner;
    }

    private static void assertForInnerList(final List<?> list) {
        try {
            Types.assertAnyListIsValidForConfig(list);
        } catch (final InvalidKeysException ex) {  // Invalid keys are prioritized over invalid values.
            throw new IllegalArgumentException(ex.buildExceptionMessage());
        } catch (final InvalidValuesException ex) {
            throw new IllegalArgumentException(ex.buildExceptionMessage());
        }
    }

    private final List<Object> inner;
}
