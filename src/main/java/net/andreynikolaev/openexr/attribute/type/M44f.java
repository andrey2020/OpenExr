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
package net.andreynikolaev.openexr.attribute.type;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import net.andreynikolaev.openexr.OpenExrUtil;
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public final class M44f implements BaseAttributeType {
    
    private final int COUNT = 0x10;
    private float[] floatArrayValue = new float[COUNT];
    
    public M44f(float[] floatArray) throws OpenExrAttributeException{
        if(floatArray.length != COUNT) throw new OpenExrAttributeException();
        this.floatArrayValue = floatArray;
    }

    public M44f(ByteBuffer byteBuffer) {
        for (int i = 0; i < COUNT; i++) {
            floatArrayValue[i] = byteBuffer.getFloat();
        }
    }

    @Override
    public String toString() {
        return OpenExrUtil.arrayToString(floatArrayValue);
    }
    
    @Override
    public String getTypeName() {
        return "m44f";
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(getValueSize())
                .order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < COUNT; i++) {
            byteBuffer.putFloat(floatArrayValue[i]);
        }
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public int getValueSize() {
        return OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT * COUNT;
    }

}
