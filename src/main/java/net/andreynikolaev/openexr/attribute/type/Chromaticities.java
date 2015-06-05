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
public class Chromaticities implements BaseAttributeType{
    private final String typeName = "chromaticities";
    
    private float redX;
    private float redY;
    private float greenX;
    private float greenY;
    private float blueX;
    private float blueY;
    private float whiteX;
    private float whiteY;

    public Chromaticities(ByteBuffer byteBuffer) {
        this.redX = byteBuffer.getFloat();
        this.redY = byteBuffer.getFloat();
        this.greenX = byteBuffer.getFloat();
        this.greenY = byteBuffer.getFloat();
        this.blueX = byteBuffer.getFloat();
        this.blueY = byteBuffer.getFloat();
        this.whiteX = byteBuffer.getFloat();
        this.whiteY = byteBuffer.getFloat();
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(OpenExrUtil.COUNT_BYTE_FOR_8INT_OR_FLOAT).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putFloat(redX);
        byteBuffer.putFloat(redY);
        byteBuffer.putFloat(greenX);
        byteBuffer.putFloat(greenY);
        byteBuffer.putFloat(blueX);
        byteBuffer.putFloat(blueY);
        byteBuffer.putFloat(whiteX);
        byteBuffer.putFloat(whiteY);
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public String toString() {
        return "redX=" + OpenExrUtil.getFloatFormat(redX) + ", " +
                "redY=" + OpenExrUtil.getFloatFormat(redY) + ", " +
                "greenX=" + OpenExrUtil.getFloatFormat(greenX) + ", " +
                "greenY=" + OpenExrUtil.getFloatFormat(greenY) + ", " +                
                "blueX=" + OpenExrUtil.getFloatFormat(blueX) + ", " +
                "blueY=" + OpenExrUtil.getFloatFormat(blueY) + ", " +
                "whiteX=" + OpenExrUtil.getFloatFormat(whiteX) + ", " +
                "whiteY=" + OpenExrUtil.getFloatFormat(whiteY) + ", "
        ;
    }
    
    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public int getValueSize() {
        return OpenExrUtil.COUNT_BYTE_FOR_8INT_OR_FLOAT;
    }
    
    
    public float getRedX() {
        return redX;
    }

    public void setRedX(float redX) {
        this.redX = redX;
    }

    public float getRedY() {
        return redY;
    }

    public void setRedY(float redY) {
        this.redY = redY;
    }

    public float getGreenX() {
        return greenX;
    }

    public void setGreenX(float greenX) {
        this.greenX = greenX;
    }

    public float getGreenY() {
        return greenY;
    }

    public void setGreenY(float greenY) {
        this.greenY = greenY;
    }

    public float getBlueX() {
        return blueX;
    }

    public void setBlueX(float blueX) {
        this.blueX = blueX;
    }

    public float getBlueY() {
        return blueY;
    }

    public void setBlueY(float blueY) {
        this.blueY = blueY;
    }

    public float getWhiteX() {
        return whiteX;
    }

    public void setWhiteX(float whiteX) {
        this.whiteX = whiteX;
    }

    public float getWhiteY() {
        return whiteY;
    }

    public void setWhiteY(float whiteY) {
        this.whiteY = whiteY;
    }
    
}
