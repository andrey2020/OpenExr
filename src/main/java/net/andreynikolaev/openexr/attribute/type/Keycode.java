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
public class Keycode implements BaseAttributeType {
    
    private int filmMfcCode;
    private int filmType;
    private int prefix;
    private int count;
    private int perfOffset;
    private int perfsPerFrame;
    private int perfsPerCount;

    public Keycode(int filmMfcCode, int filmType, int prefix, int count, int perfOffset, int perfsPerFrame, int perfsPerCount) {
        this.filmMfcCode = filmMfcCode;
        this.filmType =  filmType;
        this.prefix = prefix;
        this.count = count;
        this.perfOffset = perfOffset;
        this.perfsPerFrame = perfsPerFrame;
        this.perfsPerCount = perfsPerCount;
        
    }

    public Keycode(ByteBuffer byteBuffer) {
        this.filmMfcCode = byteBuffer.getInt();
        this.filmType = byteBuffer.getInt();
        this.prefix = byteBuffer.getInt();
        this.count = byteBuffer.getInt();
        this.perfOffset = byteBuffer.getInt();
        this.perfsPerFrame = byteBuffer.getInt();
        this.perfsPerCount = byteBuffer.getInt();        
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("filmMfcCode = ")
              .append(filmMfcCode)
              .append(", ")  
              .append("filmType = ")
              .append(filmType)
              .append(", ")
              .append("prefix = ")
              .append(prefix)
              .append(", ")
              .append("count = ")
              .append(count)
              .append(", ")
              .append("perfOffset = ")
              .append(perfOffset)
              .append(", ")
              .append("perfsPerFrame = ")
              .append(perfsPerFrame)
              .append(", ")
              .append("perfsPerCount = ")
              .append(perfsPerCount);
        
        return result.toString();
    }
    
    @Override
    public String getTypeName() {
        return "keycode";
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(getValueSize())
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(filmMfcCode)
                .putInt(filmType)
                .putInt(prefix)
                .putInt(count)
                .putInt(perfOffset)
                .putInt(perfsPerFrame)
                .putInt(perfsPerCount);
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public int getValueSize() {
        return OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT * 7;
    }

    public int getFilmMfcCode() {
        return filmMfcCode;
    }

    public void setFilmMfcCode(int filmMfcCode) {
        this.filmMfcCode = filmMfcCode;
    }

    public int getFilmType() {
        return filmType;
    }

    public void setFilmType(int filmType) {
        this.filmType = filmType;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPerfOffset() {
        return perfOffset;
    }

    public void setPerfOffset(int perfOffset) {
        this.perfOffset = perfOffset;
    }

    public int getPerfsPerFrame() {
        return perfsPerFrame;
    }

    public void setPerfsPerFrame(int perfsPerFrame) {
        this.perfsPerFrame = perfsPerFrame;
    }

    public int getPerfsPerCount() {
        return perfsPerCount;
    }

    public void setPerfsPerCount(int perfsPerCount) {
        this.perfsPerCount = perfsPerCount;
    }
    
    
}
