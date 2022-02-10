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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class InvalidKeysException extends Exception {
    private InvalidKeysException() {
        super();
        this.invalidKeys = null;
    }

    private InvalidKeysException(final ArrayList<String> keys) {
        super();
        this.invalidKeys = Collections.unmodifiableList(keys);
    }

    String buildExceptionMessage() {
        final StringBuilder builder = new StringBuilder();
        builder.append("The map contains invalid key(s) under [");
        builder.append(this.invalidKeys.stream().map(s -> {
            if (s.isEmpty()) {
                return "(top-level)";
            } else {
                return "\"" + s + "\"";
            }
        }).collect(Collectors.joining(", ")));
        builder.append("].");
        return builder.toString();
    }

    static class Builder {
        Builder() {
            this.hasInvalidKey = false;
            this.children = new LinkedHashMap<>();
        }

        Builder flag() {
            this.hasInvalidKey = true;
            return this;
        }

        Builder add(final String key, final InvalidKeysException ex) {
            this.children.put(key, ex);
            return this;
        }

        synchronized void throwIfPresent() throws InvalidKeysException {
            if (this.hasInvalidKey || !this.children.isEmpty()) {
                final ArrayList<String> invalidKeys = new ArrayList<>();
                if (this.hasInvalidKey) {
                    invalidKeys.add("");
                }
                for (final Map.Entry<String, InvalidKeysException> entry : this.children.entrySet()) {
                    for (final String innerKey : entry.getValue().invalidKeys) {
                        if (innerKey.isEmpty()) {
                            invalidKeys.add(entry.getKey());
                        } else if (innerKey.startsWith("[")) {
                            invalidKeys.add(entry.getKey() + innerKey);
                        } else {
                            invalidKeys.add(entry.getKey() + "." + innerKey);
                        }
                    }
                }
                throw new InvalidKeysException(invalidKeys);
            }
        }

        private final LinkedHashMap<String, InvalidKeysException> children;
        private boolean hasInvalidKey;
    }

    private final List<String> invalidKeys;
}
