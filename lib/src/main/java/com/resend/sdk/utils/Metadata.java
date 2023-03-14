/*
 * The MIT License
 * Copyright © 2023 Plus Five Five, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.resend.sdk.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Metadata {
    protected static Object parse(String name, Object metadata, Field field)
            throws IllegalArgumentException, IllegalAccessException {
        SpeakeasyMetadata md = field.getAnnotation(SpeakeasyMetadata.class);
        if (md == null) {
            return null;
        }

        String mdValue = md.value();

        if (mdValue == null || mdValue.isBlank()) {
            return null;
        }

        String[] groups = mdValue.split(" ");

        boolean handled = false;

        for (String group : groups) {
            String[] parts = group.split(":");
            if (parts.length != 2) {
                return null;
            }

            if (!parts[0].equals(name)) {
                continue;
            }

            Map<String, String> values = new HashMap<>();

            String[] pairs = parts[1].split(",");
            for (String pair : pairs) {
                String[] keyVal = pair.split("=");
                String key = keyVal[0];

                String val = "";
                if (keyVal.length > 1) {
                    val = keyVal[1];
                }

                values.put(key, val);
            }

            Field[] fields = metadata.getClass().getFields();

            for (Field f : fields) {
                f.setAccessible(true);
                if (values.containsKey(f.getName())) {
                    String val = values.get(f.getName());

                    if (f.getType().equals(boolean.class) || f.getType().equals(Boolean.class)) {
                        f.set(metadata, val.equals("true") || val.isBlank());
                    } else {
                        f.set(metadata, val);
                    }
                }
            }

            handled = true;
        }

        if (!handled) {
            return null;
        }

        return metadata;
    }
}