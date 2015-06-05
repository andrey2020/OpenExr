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
package net.andreynikolaev.openexr.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import net.andreynikolaev.openexr.OpenExrUtil;
import net.andreynikolaev.openexr.attribute.type.BaseAttributeType;
import net.andreynikolaev.openexr.attribute.type.Box2f;
import net.andreynikolaev.openexr.attribute.type.Box2i;
import net.andreynikolaev.openexr.attribute.type.ChannelList;
import net.andreynikolaev.openexr.attribute.type.Chromaticities;
import net.andreynikolaev.openexr.attribute.type.Compression;
import net.andreynikolaev.openexr.attribute.type.DoubleTypeAttribute;
import net.andreynikolaev.openexr.attribute.type.Envmap;
import net.andreynikolaev.openexr.attribute.type.FloatTypeAttribute;
import net.andreynikolaev.openexr.attribute.type.IntegerTypeAttribute;
import net.andreynikolaev.openexr.attribute.type.Keycode;
import net.andreynikolaev.openexr.attribute.type.LineOrder;
import net.andreynikolaev.openexr.attribute.type.M33f;
import net.andreynikolaev.openexr.attribute.type.M44f;
import net.andreynikolaev.openexr.attribute.type.Preview;
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;
import static net.andreynikolaev.openexr.exceptions.OpenExrAttributeException.ARRAY_SIZE;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class AttributeTypeTest {
    
    public static final ByteBuffer COMPRESSION_TYPE = ByteBuffer.wrap(new byte[] {(byte)3});
    
    public static final ByteBuffer ENVMAP_TYPE = ByteBuffer.wrap(new byte[] {(byte)0});
    
    public static final ByteBuffer byteArray_Channel = ByteBuffer.wrap(new byte[] {
        0x01, 0x00, // channel name 
        0x00, 0x00, 0x00, 0x00, // pixel type
        0x00, // pLinear
        0x00, 0x00, 0x00, // three chars, should be zero
        0x00, 0x00, 0x00, 0x00, // xSampling
        0x00, 0x00, 0x00, 0x00, // ySampling
        0x00 // Null Point
    }).order(ByteOrder.LITTLE_ENDIAN);

    public static final ByteBuffer PREVIEW_ARRAY = ByteBuffer.wrap(new byte[] {
        0x01, 0x00, 0x00, 0x00, 
        0x01, 0x00, 0x00, 0x00, 
        0x00, 0x01,
        0x00, 0x01,
        0x00, 0x01,
        0x00, 0x01,
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    public static final ByteBuffer byteArray64 = ByteBuffer.wrap(new byte[] {
        0x00, 0x00, 0x07, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, 0x00, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, 0x07, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, 0x00, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    public static final ByteBuffer byteArray36 = ByteBuffer.wrap(new byte[] {
        0x00, 0x00, 0x07, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, 0x00, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, 0x00, 0x10,
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    public static final ByteBuffer byteArray32 = ByteBuffer.wrap(new byte[] {
        0x00, 0x00, (byte)0x80, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, (byte)0x80, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    public static final ByteBuffer byteArray28 = ByteBuffer.wrap(new byte[] {
        0x00, 0x00, (byte)0x80, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
        0x00, 0x00, (byte)0x80, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
    }).order(ByteOrder.LITTLE_ENDIAN);

    public static final ByteBuffer byteArray16 = ByteBuffer.wrap(new byte[] {
        0x00, 0x00, (byte)0x80, 0x3F, 
        0x00, 0x00, 0x00, 0x40, 
        0x00, 0x00, 0x00, 0x20,
        0x00, 0x00, 0x00, 0x10,
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    public static final ByteBuffer byteArray8 = ByteBuffer.wrap(new byte[] {
        0x00, 0x01, 0x02, 0x03, 
        0x04, 0x05, 0x06, 0x07, 
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    public static final ByteBuffer byteArray4 = ByteBuffer.wrap(new byte[] {
        0x00, 0x01, 0x02, 0x03
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    public static final ByteBuffer byteArray_FAIL = ByteBuffer.wrap(new byte[] {
        0x00, 0x01, 0x02, 0x03, 0x00, 0x00
    }).order(ByteOrder.LITTLE_ENDIAN);
    
    HashMap<String, BaseAttributeType> typeList = new HashMap<>();
    
    public AttributeTypeTest(){
        
    }

    @Test
    public void allAttributeTypeWriteTest_Success(){
        try {
            System.out.println("Test Box2f");
            Box2f b2f = getBox2f();
            System.out.println("Box2f: " + b2f);            
            Assert.assertEquals(OpenExrUtil.toHexString(b2f.getRowData().array()), OpenExrUtil.toHexString(byteArray16.array()));            
            
            System.out.println("Test Box2i");
            Box2i b2i = getBox2i();
            System.out.println("Box2i: " + b2i);            
            Assert.assertEquals(OpenExrUtil.toHexString(b2i.getRowData().array()), OpenExrUtil.toHexString(byteArray16.array()));
            
            System.out.println("Test ChannelList");
            ChannelList channelList = getChannelList();
            System.out.println("ChannelList: " + channelList);            
            Assert.assertEquals(OpenExrUtil.toHexString(channelList.getRowData().array()), OpenExrUtil.toHexString(byteArray_Channel.array()));
            
            System.out.println("Test Chromaticities");
            Chromaticities chromaticities = getChromaticities();
            System.out.println("Chromaticities: " + chromaticities);            
            Assert.assertEquals(OpenExrUtil.toHexString(chromaticities.getRowData().array()), OpenExrUtil.toHexString(byteArray32.array()));
            
            System.out.println("Test Compression");
            Compression compression = getCompression();
            System.out.println("Compression: " + compression);            
            Assert.assertEquals(OpenExrUtil.toHexString(compression.getRowData().array()), OpenExrUtil.toHexString(COMPRESSION_TYPE.array()));
            
            System.out.println("Test DoubleTypeAttribute");
            DoubleTypeAttribute doubleTypeAttribute = getDoubleTypeAttribute();
            System.out.println("DoubleTypeAttribute: " + doubleTypeAttribute);            
            Assert.assertEquals(OpenExrUtil.toHexString(doubleTypeAttribute.getRowData().array()), OpenExrUtil.toHexString(byteArray8.array()));
            
            System.out.println("Test Envmap");
            Envmap envmap = getEnvmap();
            System.out.println("Envmap: " + envmap);            
            Assert.assertEquals(OpenExrUtil.toHexString(envmap.getRowData().array()), OpenExrUtil.toHexString(ENVMAP_TYPE.array()));
            
            System.out.println("Test FloatTypeAttribute");
            FloatTypeAttribute floatTypeAttribute = getFloatTypeAttribute();
            System.out.println("FloatTypeAttribute: " + floatTypeAttribute);            
            Assert.assertEquals(OpenExrUtil.toHexString(floatTypeAttribute.getRowData().array()), OpenExrUtil.toHexString(byteArray4.array()));
            
            System.out.println("Test IntegerTypeAttribute");
            IntegerTypeAttribute integerTypeAttribute = getIntegerTypeAttribute();
            System.out.println("IntegerTypeAttribute: " + integerTypeAttribute);            
            Assert.assertEquals(OpenExrUtil.toHexString(integerTypeAttribute.getRowData().array()), OpenExrUtil.toHexString(byteArray4.array()));
            
            System.out.println("Test Keycode");
            Keycode keycode = getKeycode();
            System.out.println("Keycode: " + keycode);            
            Assert.assertEquals(OpenExrUtil.toHexString(keycode.getRowData().array()), OpenExrUtil.toHexString(byteArray28.array()));
            
            System.out.println("Test LineOrder");
            LineOrder lineOrder = getLineOrder();
            System.out.println("LineOrder: " + lineOrder);            
            Assert.assertEquals(OpenExrUtil.toHexString(lineOrder.getRowData().array()), OpenExrUtil.toHexString(ENVMAP_TYPE.array()));
            
            System.out.println("Test M33f");
            M33f m33f = getM33f();
            System.out.println("M33f: " + m33f);            
            Assert.assertEquals(OpenExrUtil.toHexString(m33f.getRowData().array()), OpenExrUtil.toHexString(byteArray36.array()));
            
            System.out.println("Test M44f");
            M44f m44f = getM44f();
            System.out.println("M44f: " + m44f);            
            Assert.assertEquals(OpenExrUtil.toHexString(m44f.getRowData().array()), OpenExrUtil.toHexString(byteArray64.array()));
            
            System.out.println("Test Preview");
            Preview preview = getPreview();
            System.out.println("Preview: " + preview);            
            Assert.assertEquals(OpenExrUtil.toHexString(preview.getRowData().array()), OpenExrUtil.toHexString(PREVIEW_ARRAY.array()));
            
            System.out.println("############################################");
             
        } catch (OpenExrAttributeException ex) {
            
        }

        
    }
    
    @Test
    public void allAttributeTypeWriteTest_Failure(){
        String exceptionMessage = "";

        try {
            Box2f b2f = new Box2f(byteArray_FAIL);
        } catch (OpenExrAttributeException ex) {
            exceptionMessage = ex.getMessage();
        }
        System.err.println(exceptionMessage);
        assertEquals(String.format(ARRAY_SIZE, OpenExrUtil.COUNT_BYTE_FOR_4INT_OR_FLOAT, byteArray_FAIL.limit()), exceptionMessage);
    }
    
    
    
    private Box2f getBox2f() throws OpenExrAttributeException{
        Box2f result = new Box2f(byteArray16);
        byteArray16.position(0);
        
        return result;
    }
    
    private Box2i getBox2i() throws OpenExrAttributeException{
        Box2i result = new Box2i(byteArray16);
        byteArray16.position(0);
        return result;
    }
    
    private ChannelList getChannelList(){
        //Channel ch = new ChannelList.Channel(byteArray_Channel);
        ChannelList result = new ChannelList(byteArray_Channel);
        byteArray_Channel.position(0);
        return result;
    }
    
    private Chromaticities getChromaticities(){
        Chromaticities result = new Chromaticities(byteArray32);
        byteArray32.position(0);
        return result;
    }
    
    
    private Compression getCompression() throws OpenExrAttributeException{
        Compression result = Compression.getByNumber(COMPRESSION_TYPE);
        COMPRESSION_TYPE.position(0);
        return result;
    }
    
    private DoubleTypeAttribute getDoubleTypeAttribute(){
        DoubleTypeAttribute result = new DoubleTypeAttribute(byteArray8);
        byteArray8.position(0);
        return result;
    }
    
    private Envmap getEnvmap() throws OpenExrAttributeException{
        Envmap result = Envmap.getByNumber(ENVMAP_TYPE);
        ENVMAP_TYPE.position(0);
        return result;
    }
    
    private FloatTypeAttribute getFloatTypeAttribute(){
        FloatTypeAttribute result = new FloatTypeAttribute(byteArray4);
        byteArray4.position(0);        
        return result;
    }
    
    private IntegerTypeAttribute getIntegerTypeAttribute(){
        IntegerTypeAttribute result = new IntegerTypeAttribute(byteArray4);
        byteArray4.position(0);
        return result;
    }
    
    private Keycode getKeycode(){
        Keycode result = new Keycode(byteArray28);
        byteArray28.position(0);
        return result;
    }
    
    private LineOrder getLineOrder() throws OpenExrAttributeException{
        LineOrder result = LineOrder.getByNumber(ENVMAP_TYPE);
        ENVMAP_TYPE.position(0);
        return result;
    }
    
    private M33f getM33f(){
        M33f result = new M33f(byteArray36);
        byteArray36.position(0);
        return result;
    }
    
    private M44f getM44f(){
        M44f result = new M44f(byteArray64);
        byteArray64.position(0);
        return result;
    }
   
    private Preview getPreview(){
        Preview result = new Preview(PREVIEW_ARRAY);
        PREVIEW_ARRAY.position(0);
        return result;
    }
    /*
    private StringTypeAttribute getStringTypeAttribute(){
        StringTypeAttribute result = new StringTypeAttribute();
        
        return result;
    }
    
    private StringVector getStringVector(){
        StringVector result = new StringVector();
        
        return result;
    }
    
    private TileDesc getTileDesc(){
        TileDesc result = new TileDesc();
        
        return result;
    }
    
    private TimeCode getTimeCode(){
        TimeCode result = new TimeCode();
        
        return result;
    }
    
    private UnknownAttributeType getUnknownAttributeType(){
        UnknownAttributeType result = new UnknownAttributeType();
        
        return result;
    }
    
    private V2f getV2f(){
        V2f result = new V2f();
        
        return result;
    }    
    
    private V2i getV2i(){
        V2i result = new V2i();
        
        return result;
    }
    
    private V3f getV3f(){
        V3f result = new V3f();
        
        return result;
    }
    
    private V3i getV3i(){
        V3i result = new V3i();
        
        return result;
    }
*/
    
}
