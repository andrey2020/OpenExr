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
public class V2f implements BaseAttributeType{
    private final String typeName = "v2f";
    private float x;
    private float y;
    
    public  V2f(ByteBuffer byteBuffer){
        this.x = byteBuffer.getFloat();
        this.y = byteBuffer.getFloat();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    @Override
    public String toString(){
        return "x=" + x + ", " +
               "y=" + y + ", "
                ;
    }
    
    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public ByteBuffer getRowData() {
        return ByteBuffer.allocate(OpenExrUtil.COUNT_BYTE_FOR_2INT_OR_FLOAT).order(ByteOrder.LITTLE_ENDIAN).putFloat(x).putFloat(y);
    }

    @Override
    public int getValueSize() {
        return 8;
    }
}
