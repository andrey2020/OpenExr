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



/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class Preview implements BaseAttributeType {
    private final String typeName = "preview";
    
    private int width;
    private int height;    
    private char[] pixelData;
    
    
    
    public Preview(ByteBuffer byteBuffer) {
        this.width = byteBuffer.getInt();
        this.height = byteBuffer.getInt();
        this.pixelData = new char[this.width * this.height * 4];
        
        for (int i = 0; i < this.pixelData.length; i++) {
            this.pixelData[i] = byteBuffer.getChar();
        }
        
    }

    @Override
    public String toString() {
        return "";
    }
    
    @Override
    public String getTypeName() {
        return "preview";
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(getValueSize())
                .order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(this.width);
        byteBuffer.putInt(this.height);
        
        for (int i = 0; i < (getValueSize()-8)/2; i++) {
            byteBuffer.putChar(pixelData[i]);
        }
        
        return byteBuffer;
    }

    @Override
    public int getValueSize() {
        return (4 + 4) + (this.pixelData.length) * 2;
    }
}
