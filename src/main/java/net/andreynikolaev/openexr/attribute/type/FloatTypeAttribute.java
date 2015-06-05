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


/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public final class FloatTypeAttribute implements BaseAttributeType {
    private final String typeName = "float";
    private float floatVlue;

    public FloatTypeAttribute(float floatValue) {
        this.floatVlue = floatValue;
    }
    
    public FloatTypeAttribute(ByteBuffer byteBuffer) {
        this.floatVlue = byteBuffer.getFloat();
    }

    @Override
    public String toString() {
        return "" + OpenExrUtil.getFloatFormat(floatVlue);
    }
    
        @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putFloat(floatVlue);
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public int getValueSize() {
        return OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT;
    }

    public float getFloatVlue() {
        return floatVlue;
    }

    public void setFloatVlue(float floatVlue) {
        this.floatVlue = floatVlue;
    } 
}
