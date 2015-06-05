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
public final class Box2i implements BaseAttributeType{
    private final String typeName = "box2i";
    private final int typeSize = OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    
    public Box2i(int xMin, int xMax, int yMin, int yMax){
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    
    public  Box2i(ByteBuffer byteBuffer) throws OpenExrAttributeException{
        if(byteBuffer.limit() != OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT) throw new OpenExrAttributeException(OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT, byteBuffer.limit());
        this.xMin = byteBuffer.getInt();
        this.yMin= byteBuffer.getInt();
        this.xMax = byteBuffer.getInt();
        this.yMax = byteBuffer.getInt();
    }

    public int getxMin() {
        return xMin;
    }

    public void setxMin(int xMin) {
        this.xMin = xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public void setyMin(int yMin) {
        this.yMin = yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }
    
    @Override
    public String toString(){
        return "xMin=" + xMin + ", " +
                "yMin=" + yMin + ", " +
                "xMax=" + xMax + ", " +
                "yMax=" + yMax + ", "
                ;
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(xMin)
                .putInt(yMin)
                .putInt(xMax)
                .putInt(yMax);
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }
    
    @Override
    public int getValueSize() {
        return typeSize;
    }

    
}
