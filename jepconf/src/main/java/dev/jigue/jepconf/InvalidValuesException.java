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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

final class InvalidValuesException extends Exception {
    private InvalidValuesException(final LinkedHashMap<String, String> invalidValues) {
        super();
        this.invalidValues = Collections.unmodifiableMap(invalidValues);
    }

    static InvalidValuesException of(final String error) {
        final LinkedHashMap<String, String> invalidValues = new LinkedHashMap<>();
        invalidValues.put("", error);
        return new InvalidValuesException(invalidValues);
    }

    String buildExceptionMessage() {
        final StringBuilder builder = new StringBuilder();
        builder.append("The map contains invalid value(s), [");
        builder.append(this.invalidValues.entrySet().stream().map(e -> {
            return "at \"" + e.getKey() + "\" => " + e.getValue();
        }).collect(Collectors.joining(", ")));
        builder.append("].");
        return builder.toString();
    }

    static class Builder {
        Builder() {
            this.children = new LinkedHashMap<>();
        }

        Builder add(final String key, final InvalidValuesException ex) {
            this.children.put(key, ex);
            return this;
        }

        synchronized void throwIfPresent() throws InvalidValuesException {
            if (!this.children.isEmpty()) {
                final LinkedHashMap<String, String> invalidValues = new LinkedHashMap<>();
                for (final Map.Entry<String, InvalidValuesException> child : this.children.entrySet()) {
                    for (final Map.Entry<String, String> entry : child.getValue().invalidValues.entrySet()) {
                        if (entry.getKey().isEmpty()) {
                            invalidValues.put(child.getKey(), entry.getValue());
                        } else if (entry.getKey().startsWith("[")) {
                            invalidValues.put(child.getKey() + entry.getKey(), entry.getValue());
                        } else {
                            invalidValues.put(child.getKey() + "." + entry.getKey(), entry.getValue());
                        }
                    }
                }
                throw new InvalidValuesException(invalidValues);
            }
        }

        private final LinkedHashMap<String, InvalidValuesException> children;
    }

    private final Map<String, String> invalidValues;
}
