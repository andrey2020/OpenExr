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
public class DoubleTypeAttribute implements BaseAttributeType {
    private final String typeName = "double";
    private double doubleValue;

    public DoubleTypeAttribute(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public DoubleTypeAttribute(ByteBuffer byteBuffer) {
        this.doubleValue = byteBuffer.getDouble();
    }

    @Override
    public String toString() {
        return OpenExrUtil.getFloatFormat(doubleValue);
    }
    
        @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(OpenExrUtil.COUNT_BYTE_FOR_LONG_OR_DOUBLE)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putDouble(doubleValue);
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public int getValueSize() {
        return OpenExrUtil.COUNT_BYTE_FOR_LONG_OR_DOUBLE;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    
}
