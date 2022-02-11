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

import java.util.List;
import java.util.Map;

final class Types {
    private Types() {
        // No instantiation.
    }

    /**
     * Returns a nickname for a Class object.
     *
     * <ul>
     * <li>Primitive type names for boxing classes.
     * <li>"List" for ConfigList.
     * <li>"Map" for ConfigMap.
     * <li>A simple name for classes under "java.lang".
     * </ul>
     *
     * @param klass  a Class object
     * @return the nickname for the specified Class
     */
    static String nick(final Class<?> klass) {
        if (klass == null) {
            return "(null)";
        }

        if (Boolean.class.equals(klass)) {
            return "boolean";
        } else if (Long.class.equals(klass)) {
            return "long";
        } else if (Double.class.equals(klass)) {
            return "double";
        } else if (Byte.class.equals(klass)) {
            return "byte";
        } else if (Short.class.equals(klass)) {
            return "short";
        } else if (Integer.class.equals(klass)) {
            return "int";
        } else if (Character.class.equals(klass)) {
            return "char";
        } else if (ConfigList.class.equals(klass)) {
            return "List";
        } else if (ConfigMap.class.equals(klass)) {
            return "Map";
        } else if (Package.getPackage("java.lang").equals(klass.getPackage())) {
            return klass.getSimpleName();
        }
        return klass.getName();
    }

    /**
     * Throws an Exception if the specified List is not valid to be converted into ConfigList.
     *
     * <p>It checks the specified List recursively, also for inner Lists and Maps. I may throw InvalidKeysException
     * as the specified List may contain another List/Map that contains an invalid Map in it.
     *
     * <p>It only asserts for an List. It does not perform any actual conversion. It means that ConfigList
     * and ConfigMap are expected to perform "two-pass" conversion: assertion pass and actual conversion pass.
     *
     * @throws InvalidKeysException  if the List contains at least one Map whose key is invalid
     * @throws InvalidValuesException  if the List contains at least one invalid Object without any Map whose key is invalid
     */
    static void assertAnyListIsValidForConfig(final List<?> list) throws InvalidKeysException, InvalidValuesException {
        final InvalidKeysException.Builder invalidKeysExceptions = new InvalidKeysException.Builder();
        final InvalidValuesException.Builder invalidValuesExceptions = new InvalidValuesException.Builder();

        int i = 0;
        for (final Object element : list) {
            try {
                assertAnyObjectIsValidForConfig(element);
            } catch (final InvalidKeysException ex) {
                // Invalid keys are prioritized over invalid values.
                invalidKeysExceptions.add("[" + Integer.toString(i) + "]", ex);
            } catch (final InvalidValuesException ex) {
                invalidValuesExceptions.add("[" + Integer.toString(i) + "]", ex);
            } finally {
                i++;
            }
        }

        invalidKeysExceptions.throwIfPresent();  // Invalid keys are prioritized over invalid values.
        invalidValuesExceptions.throwIfPresent();
    }

    /**
     * Throws an Exception if the specified Map is not valid to be converted into ConfigMap.
     *
     * <p>It checks the specified Map recursively, also for inner Lists and Maps.
     *
     * <p>It only asserts for an List. It does not perform any actual conversion. It means that ConfigList
     * and ConfigMap are expected to perform "two-pass" conversion: assertion pass and actual conversion pass.
     *
     * @throws InvalidKeysException  if the Map contains at least one invalid key, or one Map whose key is invalid in it
     * @throws InvalidValuesException  if the Map contains at least one invalid Object in it
     */
    static void assertAnyMapIsValidForConfig(final Map<?, ?> map) throws InvalidKeysException, InvalidValuesException {
        final InvalidKeysException.Builder invalidKeysExceptions = new InvalidKeysException.Builder();
        final InvalidValuesException.Builder invalidValuesExceptions = new InvalidValuesException.Builder();

        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                invalidKeysExceptions.flag();  // This map has at least one invalid non-String key directly in it.
                continue;  // If this map has at least one invalid key, the entire map is considered invalid immediately.
            }

            final String key = (String) entry.getKey();
            if (key.contains(".") || key.contains("[") || key.contains("]")) {
                invalidKeysExceptions.flag();  // This map has at least one invalid key with containing ".[]" directly in it.
                continue;  // If this map has at least one invalid key, the entire map is considered invalid immediately.
            }

            try {
                assertAnyObjectIsValidForConfig(entry.getValue());
            } catch (final InvalidKeysException ex) {
                // Invalid keys are prioritized over invalid values.
                invalidKeysExceptions.add(key, ex);
            } catch (final InvalidValuesException ex) {
                invalidValuesExceptions.add(key, ex);
            }
        }

        invalidKeysExceptions.throwIfPresent();  // Invalid keys are prioritized over invalid values.
        invalidValuesExceptions.throwIfPresent();
    }

    static Object normalizeObject(final Object object, final boolean mutable) {
        if (isValidObjectInConfig(object)) {
            return object;
        } else if (object instanceof CharSequence) {
            return object.toString();
        } else if (object instanceof Number) {
            if (object instanceof Integer) {
                return (Long) ((Integer) object).longValue();
            } else if (object instanceof Float) {
                return (Double) ((Float) object).doubleValue();
            } else if (object instanceof Short) {
                return (Long) ((Short) object).longValue();
            } else if (object instanceof Byte) {
                return (Long) ((Byte) object).longValue();
            }
        } else if (object instanceof Map) {
            if (mutable) {
                return ConfigMap.mutableSnapshotOfAnyMap((Map<?, ?>) object);
            } else {
                return ConfigMap.snapshotOfAnyMap((Map<?, ?>) object);
            }
        } else if (object instanceof List) {
            if (mutable) {
                return ConfigList.mutableSnapshotOf((List<?>) object);
            } else {
                return ConfigList.snapshotOf((List<?>) object);
            }
        }
        throw new IllegalArgumentException("The specified object cannot be normalized: " + object.getClass().toString());
    }

    /**
     * Throws an Exception if the specified Object is not valid to be converted into ConfigList or ConfigMap.
     *
     * <p>It checks the specified Object recursively against List and Map. I may throw InvalidKeysException
     * as the specified Object may be an invalid Map, or another List/Map that contains an invalid Map in it.
     *
     * <p>It only asserts for an Object. It does not perform any actual conversion. It means that ConfigList
     * and ConfigMap are expected to perform "two-pass" conversion: assertion pass and actual conversion pass.
     *
     * @throws InvalidKeysException  if the Object contains at least one Map whose key is invalid
     * @throws InvalidValuesException  if the Object contains at least one invalid Object without any Map whose key is invalid
     */
    private static void assertAnyObjectIsValidForConfig(final Object object)
            throws InvalidKeysException, InvalidValuesException {
        if (isValidObjectInConfig(object)) {
            return;
        } else if (object instanceof CharSequence) {
            return;
        } else if (object instanceof Number) {
            if (object instanceof Integer
                    || object instanceof Float
                    || object instanceof Short
                    || object instanceof Byte) {
                return;
            }
        } else if (object instanceof Map) {
            assertAnyMapIsValidForConfig((Map<?, ?>) object);
            return;
        } else if (object instanceof List) {
            assertAnyListIsValidForConfig((List<?>) object);
            return;
        }

        // Only List or Map is allowed except for primitives. Any other class is considered as an invalid value.
        throw InvalidValuesException.of("invalid type <" + nick(object.getClass()) + ">");
    }

    /**
     * Returns true if the specified Object is valid directly in ConfigList or ConfigMap.
     */
    private static boolean isValidObjectInConfig(final Object object) {
        return object == null  // null is considered valid as a value (not for a key).
                || object instanceof String  // final
                || object instanceof Long  // final
                || object instanceof Double  // final
                || object instanceof Boolean  // final
                || object instanceof ConfigList  // final
                || object instanceof ConfigMap  // final
                ;
    }
}
