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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum Types {
    PRIMITIVE,
    ARRAY,
    MAP,
    OBJECT,
    ENUM;

    private static final Set<Class<?>> PRIMITIVE_TYPES = getPrimitiveWrapperTypes();

    public static Types getType(Class<?> clazz) {
        if (clazz.isArray()) {
            return Types.ARRAY;
        } else if (Map.class.isAssignableFrom(clazz)) {
            return Types.MAP;
        } else if (clazz.isEnum()) {
            return Types.ENUM;
        } else if (isPrimitiveWrapperTypes(clazz) || clazz.isPrimitive() || String.class.isAssignableFrom(clazz)) {
            return Types.PRIMITIVE;
        } else {
            return Types.OBJECT;
        }
    }

    private static boolean isPrimitiveWrapperTypes(Class<?> clazz) {
        return PRIMITIVE_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getPrimitiveWrapperTypes() {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
}