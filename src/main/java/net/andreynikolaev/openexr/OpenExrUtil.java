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
package net.andreynikolaev.openexr;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class OpenExrUtil {
    public static final byte NULL_BYTE = (byte) 0;
    
    public static final int COUNT_BYTE_FOR_CHAR = 2;
    public static final int COUNT_BYTE_FOR_INT_OR_FLOAT = 4;    
    public static final int COUNT_BYTE_FOR_2INT_OR_FLOAT = 8;
    public static final int COUNT_BYTE_FOR_LONG_OR_DOUBLE = 8;
    public static final int COUNT_BYTE_FOR_4INT_OR_FLOAT = 16;
    public static final int COUNT_BYTE_FOR_8INT_OR_FLOAT = 32;
    
    public static boolean getBitFromByte(byte data, int position){
        return ((data >> position) & 1) == 1;
    }
    
    public static byte getByteFromBitArray(boolean... f) {
        byte result = (byte)0;
        boolean[] b = new boolean[8];
        for (int i = 0; i < 8 && i < f.length; i++) {
            b[i] = f[i];
        }
        
        for (int i = 0; i < 8; i++) {
            result <<= 1;
            if (b[i])
                result |= 1;
        }
        return result;
    }

    public static String toHexString(byte[] value){
        StringBuilder sb = new StringBuilder();
        for (byte b : value) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
    
    public static String readNullTerminatedString(ByteBuffer rowData) {
        ByteBuffer result = ByteBuffer.allocate(255);
        byte b;
        int size = 0;
        while ((b = rowData.get()) != 0) {
            result.put(b);
            size++;
        }

        return new String(result.array(), StandardCharsets.UTF_8).substring(0, size);
    }
    
    public static String getFloatFormat(double f){
        return String.format("%E", f);
    }

    public static String arrayToString(float[] arrayValue) {
        StringBuilder result = new StringBuilder();
        
        for (Object value : arrayValue) {
            result.append(value);
            result.append(", ");
        }
        result.delete(result.length()-1, result.length());
        return result.toString();
    }
 
}