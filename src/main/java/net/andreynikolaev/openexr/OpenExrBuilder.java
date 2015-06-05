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
package net.andreynikolaev.openexr;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.andreynikolaev.openexr.attribute.Attributes;
import net.andreynikolaev.openexr.attribute.HeaderAttribute;
import net.andreynikolaev.openexr.attribute.type.BaseAttributeType;
import net.andreynikolaev.openexr.attribute.type.Box2i;
import net.andreynikolaev.openexr.attribute.type.Compression;
import net.andreynikolaev.openexr.attribute.type.IntegerTypeAttribute;
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;
import net.andreynikolaev.openexr.exceptions.OpenExrFormatException;

/**
 * The primary application programming interface (API) to the OpenExr.
 *
 * <h3>Application Notes</h3>
 *
 * <h4>Using this class</h4>
 *
 * <p>
 *
 * </p>
 *
 * <p>
 * All of methods provided by the Imaging class are declared static.
 * </p>
 *
 *
 * <h4>Format support</h4>
 *
 * <p>
 * </p>
 *
 *
 * <h4>Example code</h4>
 *
 * <p>
 * </p>
 *
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public final class OpenExrBuilder {

    /**
     * Create new OpenExrImage object from byte Array.
     * @param rowData 
     * @return  
     * @throws OpenExrFormatException 
     * @throws net.andreynikolaev.openexr.exceptions.OpenExrAttributeException 
     */
    public static OpenExr readAllData(byte[] rowData) throws OpenExrFormatException, OpenExrAttributeException{
        return extractExrImage(ByteBuffer.wrap(rowData).order(ByteOrder.LITTLE_ENDIAN));
    }
    
    /**
     * Create new OpenExrImage object from java.io.InputStream.
     * @param inputStream
     * @return 
     * @throws IOException
     * @throws OpenExrException 
     */
    /*
    public static OpenExr readAllData(InputStream inputStream) throws IOException, OpenExrException{
        return readAllData(IOUtils.toByteArray(inputStream));
    }
    */
    /**
     * Create new OpenExrImage object from java.io.File.
     * @param file
     * @return 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws OpenExrFormatException 
     */
    
    /*
    public static OpenExr readAllData(File file) throws FileNotFoundException, IOException, OpenExrException{
        return readAllData(new FileInputStream(file));
    }
    */
    
    /**
     * Create new OpenExrImage object by file name.
     * @param fileName
     * @return 
     * @throws IOException
     * @throws FileNotFoundException
     * @throws OpenExrFormatException 
     */
    /*
    public static OpenExr readAllData(String fileName) throws IOException, FileNotFoundException, OpenExrException{
        return readAllData(new File(fileName));
    }
*/
    /**
     * 
     * @param rowData
     * @throws OpenExrFormatException 
     */
    private static OpenExr extractExrImage(ByteBuffer rowData) throws OpenExrFormatException, OpenExrAttributeException{
        OpenExr openExr = new OpenExr();
        
        checkMagicNumber(rowData);
        
        extractFileFormatVersion(rowData, openExr);
        
        extractHeaderAttribute(rowData, openExr);
        
        extractOffsetTableSize(openExr);
        
        extractOffSetTable(rowData, openExr);
        
        extractChunkData(rowData, openExr);
        
        return openExr;
    }
    
    /**
     * 
     * @param rowData
     * @return 
     */    
    private static void checkMagicNumber(ByteBuffer rowData) throws OpenExrFormatException{
        if(OpenExr.MAGIC_NUMBER_AS_INT != rowData.getInt(0)) throw new OpenExrFormatException();
    }
    
    /**
     * 
     * @param rawData 
     */
    private static void extractFileFormatVersion(ByteBuffer rowData, OpenExr openExr){
        openExr.fileFormatVersion = rowData.get(4);
        byte flag = rowData.get(5);
        openExr.singleTileBit = OpenExrUtil.getBitFromByte(flag, 1);
        openExr.longNameBit = OpenExrUtil.getBitFromByte(flag, 2);
        openExr.nonImageBit = OpenExrUtil.getBitFromByte(flag, 3);
        openExr.multiPartBit = OpenExrUtil.getBitFromByte(flag, 4);
    }
    
    /**
     * 
     * @param rowData 
     */
    private static void extractHeaderAttribute(ByteBuffer rowData, OpenExr openExr) throws OpenExrAttributeException{
        openExr.attributeList.add(new TreeMap<String,HeaderAttribute<? extends BaseAttributeType>>());
        int i = 0;
        boolean isHeaderEnd = false;
        rowData.position(8);
        while (!isHeaderEnd) {
            String headerAttributeName = OpenExrUtil.readNullTerminatedString(rowData);
            String headerAttributeType = OpenExrUtil.readNullTerminatedString(rowData);
            int headerAttributeSize = rowData.getInt();
            HeaderAttribute headerAttribute = readHeaderAttribute(rowData, headerAttributeName, headerAttributeType, headerAttributeSize);
            
            openExr.attributeList.get(i).put(headerAttribute.getName(), headerAttribute);

            isHeaderEnd = rowData.get(rowData.position()) == 0;
            if(isHeaderEnd && openExr.multiPartBit && rowData.get(rowData.position()+1) != 0){
                rowData.get();
                isHeaderEnd = false;
                openExr.attributeList.add(new TreeMap<String,HeaderAttribute<? extends BaseAttributeType>>());
                i++;
                
            }else if(isHeaderEnd && openExr.multiPartBit && rowData.get(rowData.position()+1) == 0){
                rowData.get();
            }
        }
        
        openExr.offsetStartPosition = rowData.position();
        rowData.get();
    }
    
    /**
     * 
     * @param rowData
     * @param openExr 
     */
    private static void extractOffsetTableSize(OpenExr openExr){
        if(openExr.multiPartBit){
            for (Map<String,HeaderAttribute<? extends BaseAttributeType>> attrL : openExr.attributeList) {
                openExr.countOffsets = openExr.countOffsets + ((IntegerTypeAttribute) attrL.get("chunkCount").getValue()).getIntValue();
            }
        }else{
            HeaderAttribute<Compression> compressionAttribute = (HeaderAttribute<Compression>) openExr.attributeList.get(0).get("compression");
            HeaderAttribute<Box2i> dataWindowAttribute = (HeaderAttribute<Box2i>) openExr.attributeList.get(0).get("dataWindow");
            
            openExr.countOffsets = (dataWindowAttribute.getValue().getyMax() - dataWindowAttribute.getValue().getyMin())/ 
                                   compressionAttribute.getValue().getRegularNumberScanLinePerBlock() +1;
   
        }
    }
    
    /**
     * 
     * @param rowData 
     */
    private static void extractOffSetTable(ByteBuffer rowData, OpenExr openExr){        
        for (int i = 0; i < openExr.countOffsets; i++) {                       
            openExr.offsetTable.add(rowData.getLong());            
        }        
    }
    
    /**
     * 
     * @param rowData 
     */
    private static void extractChunkData(ByteBuffer rowData, OpenExr openExr){
        try {
            int startImageData = openExr.offsetStartPosition  + (openExr.countOffsets * 8) + 1;
            openExr.imgData = Arrays.copyOfRange(rowData.array(), startImageData, rowData.array().length);
            Map<Integer, Object> tt = new HashMap<>();
            ArrayList<Object> al = new ArrayList<>();
            
            
            
            
            openExr.offsetTable.get(0);
            
            FileChannel fch = FileChannel.open(Paths.get("testImg/demo11.exr"));
            fch.position(startImageData);
            
            
        
        
        
        
        } catch (IOException ex) {
            Logger.getLogger(OpenExrBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
     
    private static HeaderAttribute readHeaderAttribute(ByteBuffer rowData, final String name, final String type, int size) throws OpenExrAttributeException {
        HeaderAttribute headerAttribute = null;
        byte[] value = new byte[size];
        rowData.get(value, 0, size);
        try{
           headerAttribute = Attributes.valueOf(type.toUpperCase()).getNewInstance(name, ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN));
        }catch(IllegalArgumentException e){
           headerAttribute = Attributes.valueOf("UNKNOWN").getNewInstance(name, ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN));
        }
        headerAttribute.getValueSize();
        return headerAttribute;
    }
    

    
}
