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
 *implements BaseAttributeType
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public final class Box2f implements BaseAttributeType{
    public static final String typeName = "box2f";
    
    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    
    public Box2f(float xMin, float xMax, float yMin, float yMax){
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    
    public  Box2f(ByteBuffer byteBuffer) throws OpenExrAttributeException{
        if(byteBuffer.limit() != OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT) throw new OpenExrAttributeException(OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT, byteBuffer.limit());
        this.xMin = byteBuffer.getFloat();
        this.xMax = byteBuffer.getFloat();
        this.yMin = byteBuffer.getFloat();
        this.yMax = byteBuffer.getFloat();
    }

    public float getxMin() {
        return xMin;
    }
    
    public float getxMax() {
        return xMax;
    }

    public float getyMin() {
        return yMin;
    }

    public float getyMax() {
        return yMax;
    }
    
    public void setxMin(float xMin) {
        this.xMin = xMin;
    }
    
    public void setxMax(float xMax) {
        this.xMax = xMax;
    }

    public void setyMin(float yMin) {
        this.yMin = yMin;
    }

    public void setyMax(float yMax) {
        this.yMax = yMax;
    }

    @Override
    public String toString(){
        return "xMin=" + OpenExrUtil.getFloatFormat(xMin) + ", " +
                "xMax=" + OpenExrUtil.getFloatFormat(xMax) + ", " +
                "yMin=" + OpenExrUtil.getFloatFormat(yMin) + ", " +
                "yMax=" + OpenExrUtil.getFloatFormat(yMax) + ", "
                ;
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putFloat(xMin)
                .putFloat(xMax)
                .putFloat(yMin)
                .putFloat(yMax);
        byteBuffer.position(0);                
        return byteBuffer;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public int getValueSize() {
        return OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT;
    }
    
}
