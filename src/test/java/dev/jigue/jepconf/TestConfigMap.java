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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class TestConfigMap {
    @Test
    public void test0() {
        final Map<String, Object> map = new HashMap<>();
        final ConfigMap actual = assertConfigMap(map);
        assertEquals(map, actual);
    }

    @Test
    public void test1() {
        final Map<String, Object> map = new HashMap<>();
        map.put("foo", "foo");
        final ConfigMap actual = assertConfigMap(map);
        assertEquals(map, actual);
    }

    @Test
    public void test2PrimitiveConversion() {
        final Map<String, Object> map = new HashMap<>();
        map.put("byte", (byte) 123);
        map.put("short", (short) 123);
        map.put("int", (int) 123);
        map.put("long", 123L);
        map.put("float", (float) 1.23);
        map.put("double", (double) 1.23);
        final ConfigMap actual = assertConfigMap(map);
        final Object actualByte = actual.get("byte");
        final Object actualShort = actual.get("short");
        final Object actualInt = actual.get("int");
        final Object actualLong = actual.get("long");
        final Object actualFloat = actual.get("float");
        final Object actualDouble = actual.get("double");
        assertEquals(Long.class, actualByte.getClass());
        assertEquals(123L, actualByte);
        assertEquals(Long.class, actualShort.getClass());
        assertEquals(123L, actualShort);
        assertEquals(Long.class, actualInt.getClass());
        assertEquals(123L, actualInt);
        assertEquals(Long.class, actualLong.getClass());
        assertEquals(123L, actualLong);
        assertEquals(Double.class, actualFloat.getClass());
        assertEquals((double) 1.23, (double) actualFloat, 0.0000001d);
        assertEquals(Double.class, actualDouble.getClass());
        assertEquals((double) 1.23, (double) actualDouble, 0.0000001d);
    }

    @Test
    public void test3StringConversion() {
        final Map<String, Object> map = new HashMap<>();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("foo");
        stringBuilder.append("bar");
        map.put("stringBuilder", stringBuilder);
        final ConfigMap actual = assertConfigMap(map);
        final Object actualStringBuilder = actual.get("stringBuilder");
        assertEquals(String.class, actualStringBuilder.getClass());
        assertEquals("foobar", actualStringBuilder);
    }

    @Test
    public void testUnmodifiable() {
        final Map<String, Object> map = new HashMap<>();
        map.put("foo", "foo");
        map.put("bar", "bar");
        final ConfigMap actual = ConfigMap.snapshotOf(castMap(map));
        try {
            actual.put("baz", "baz");
        } catch (final UnsupportedOperationException ex) {
            return;
        }
        fail("No expected Exception is thrown.");
    }

    @Test
    public void testInvalidKey1() {
        final Map<Object, Object> map1 = new HashMap<>();
        map1.put(Integer.valueOf(12), "foo");
        final Map<Object, Object> map2 = new HashMap<>();
        map2.put(Double.valueOf(123.4), "bar");
        final Map<Object, Object> map3 = new HashMap<>();
        map3.put(Boolean.valueOf(false), "baz");
        map2.put("baz", map3);
        map1.put("foo", map2);
        try {
            ConfigMap.snapshotOfAnyMap(map1);
        } catch (final IllegalArgumentException ex) {
            assertEquals("The map contains invalid key(s) under [(top-level), \"foo\", \"foo.baz\"].", ex.getMessage());
            return;
        }
        fail("No expected Exception is thrown.");
    }

    @Test
    public void testInvalidValue1() {
        final Map<Object, Object> map1 = new HashMap<>();
        map1.put("foo", Character.valueOf('a'));
        final List<Object> list2 = new ArrayList<>();
        list2.add(Character.valueOf('b'));
        final Map<Object, Object> map3 = new HashMap<>();
        map3.put("baz", Character.valueOf('a'));
        list2.add(map3);
        map1.put("fuga", list2);
        try {
            ConfigMap.snapshotOfAnyMap(map1);
        } catch (final IllegalArgumentException ex) {
            assertEquals(
                    "The map contains invalid value(s), [at \"foo\" => invalid type <char>, "
                        + "at \"fuga[0]\" => invalid type <char>, at \"fuga[1].baz\" => invalid type <char>].",
                    ex.getMessage());
            return;
        }
    }

    private static ConfigMap assertConfigMap(final Map<? extends Object, ? extends Object> map) {
        final ConfigMap actual = ConfigMap.snapshotOf(castMap(map));
        assertTrue(actual instanceof ConfigMap);
        return actual;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> castMap(final Map<? extends Object, ? extends Object> map) {
        return (Map<String, Object>) map;
    }
}
