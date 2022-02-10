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
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestConfigList {
    @Test
    public void testOf() {
        final ConfigList list0 = ConfigList.of();
        assertEquals(0, list0.size());

        final ConfigList list1 = ConfigList.of("foo");
        assertEquals(1, list1.size());
        assertEquals("foo", list1.get(0));

        final ConfigList list2 = ConfigList.of("foo", 12);
        assertEquals(2, list2.size());
        assertEquals("foo", list2.get(0));
        assertEquals(12L, list2.get(1));

        final ConfigList list3 = ConfigList.of("foo", 12, 123.4567);
        assertEquals(3, list3.size());
        assertEquals("foo", list3.get(0));
        assertEquals(12L, list3.get(1));
        assertEquals(123.4567d, (double) list3.get(2), 0.00001d);
    }

    @Test
    public void test0() {
        final List<Object> list = new ArrayList<>();
        final ConfigList actual = assertConfigList(list);
        assertEquals(list, actual);
    }

    @Test
    public void test1() {
        final List<Object> list = new ArrayList<>();
        list.add("foo");
        final ConfigList actual = assertConfigList(list);
        assertEquals(list, actual);
    }

    @Test
    public void test2PrimitiveConversion() {
        final List<Object> list = new ArrayList<>();
        list.add((byte) 123);
        list.add((short) 123);
        list.add((int) 123);
        list.add(123L);
        list.add((float) 1.23);
        list.add((double) 1.23);
        final ConfigList actual = assertConfigList(list);
        final Object actualByte = actual.get(0);
        final Object actualShort = actual.get(1);
        final Object actualInt = actual.get(2);
        final Object actualLong = actual.get(3);
        final Object actualFloat = actual.get(4);
        final Object actualDouble = actual.get(5);
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
        final List<Object> list = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("foo");
        stringBuilder.append("bar");
        list.add(stringBuilder);
        final ConfigList actual = assertConfigList(list);
        final Object actualStringBuilder = actual.get(0);
        assertEquals(String.class, actualStringBuilder.getClass());
        assertEquals("foobar", actualStringBuilder);
    }

    @Test
    public void testUnmodifiable() {
        final List<Object> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        final ConfigList actual = ConfigList.snapshotOf(castList(list));
        try {
            actual.add("baz");
        } catch (final UnsupportedOperationException ex) {
            return;
        }
        fail("No expected Exception is thrown.");
    }

    private static ConfigList assertConfigList(final List<? extends Object> list) {
        final ConfigList actual = ConfigList.snapshotOf(castList(list));
        assertTrue(actual instanceof ConfigList);
        return actual;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> castList(final List<? extends Object> list) {
        return (List<Object>) list;
    }
}
