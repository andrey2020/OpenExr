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

import net.andreynikolaev.openexr.OpenExrUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public final class IntegerTypeAttribute implements BaseAttributeType {
    private final String typeName = "int";
    private int intValue;

    public IntegerTypeAttribute(int intValue) {
    }

    public IntegerTypeAttribute(ByteBuffer byteBuffer) {
        this.intValue = byteBuffer.getInt();
    }
    
    public int getIntValue(){
        return this.intValue;
    }

    @Override
    public String toString() {
        return "" + this.intValue;
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
                .putInt(intValue);
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public int getValueSize() {
        return OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT;
    }
}
