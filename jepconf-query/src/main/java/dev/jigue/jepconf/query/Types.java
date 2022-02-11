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

import dev.jigue.jepconf.ConfigList;
import dev.jigue.jepconf.ConfigMap;

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
}
