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
import net.andreynikolaev.openexr.OpenExrUtil;


/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public final class UnknownAttributeType implements BaseAttributeType{
    private final String typeName = "unknown";
    
    byte[] rowData;

    public UnknownAttributeType() {
        
    }

    public UnknownAttributeType(ByteBuffer byteBuffer) {
        this.rowData = byteBuffer.array();
    }

    @Override
    public String toString() {
        return OpenExrUtil.toHexString(rowData);
    }
    
        @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public ByteBuffer getRowData() {
        return ByteBuffer.allocate(rowData.length).put(rowData);
    }

    @Override
    public int getValueSize() {
        return rowData.length;
    }
    
}
