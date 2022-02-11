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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

public class TestConfigQueries {
    @Test
    public void test0() {
        final ConfigAccessor config = ConfigAccessor.onSnapshotOf(new HashMap<>());
        try {
            config.query(ConfigQueries.intQuery("n"));
        } catch (final NoSuchElementException ex) {
            assertEquals("The field \"n\" does not exist.", ex.getMessage());
            return;
        }
        fail("No expected Exception is thrown.");
    }

    @Test
    public void test1() {
        final ArrayList<Object> list = new ArrayList<>();
        list.add("hoge");
        list.add("fuga");
        final HashMap<Object, Object> inner = new HashMap<>();
        inner.put("foo", "bar");
        inner.put("baz", 12);
        final HashMap<String, Object> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("n", 1290);
        map.put("const", "ONE");
        map.put("list", list);
        map.put("map", inner);
        final ConfigAccessor config = ConfigAccessor.onSnapshotOf(map);
        assertEquals(1290, config.query(ConfigQueries.intQuery("n")));
        assertEquals("bar", config.query(ConfigQueries.stringQuery("foo")));
        assertEquals(Enum1.ONE, config.query(ConfigQueries.enumQuery("const", Enum1.class)));
        final List<String> actualList = config.getList("list", String.class);
        try {
            config.getMap("map", String.class);
        } catch (final ClassCastException ex) {
            assertEquals(
                    "The field \"map\" is Map, expected to contain String keys and String values, but contains value(s) of [long].",
                    ex.getMessage());
            return;
        }
        fail("No expected Exception is thrown.");
    }

    enum Enum1 {
        ONE,
        TWO,
        THREE,
        ;
    }
}
