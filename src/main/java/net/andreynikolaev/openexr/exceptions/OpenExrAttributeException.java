/*
 * Copyright 2015 <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.andreynikolaev.openexr.exceptions;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class OpenExrAttributeException extends OpenExrException {
      
    public static final String LINEORDER_UNKNOWN_TYPE = "Unknown LineOrder type %d";
    public static final String ENVMAP_UNKNOWN_TYPE = "Unknown envmap type %d";
    public static final String COMPRESSION_UNKNOWN_TYPE = "Unknown compression type %d";
    public static final String NULL_ATTRIBUTE_NAME = "Attribute name and attribute type must not be Null";
    public static final String RESERVED_WORD = "Reserved attribute name";
    public static final String ARRAY_SIZE = "Expected size of the array: %d , but was %d";
    
    public OpenExrAttributeException() {
        super();
    }
    
    public OpenExrAttributeException(String msg) {
        super(msg);
    }
    
    public OpenExrAttributeException(long a, long b) {
        super(String.format(ARRAY_SIZE, a, b));
    }
    
    public OpenExrAttributeException(String formatString, int type) {
        super(String.format(formatString, type));
    }
    
}
