/*3
/* 1
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

import net.andreynikolaev.openexr.datachunk.OpenExrDataChunk;
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;
import net.andreynikolaev.openexr.attribute.HeaderAttribute;
import net.andreynikolaev.openexr.attribute.type.BaseAttributeType;
import net.andreynikolaev.openexr.attribute.type.StringTypeAttribute;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import net.andreynikolaev.openexr.attribute.StandartAttribute;


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
 *
 * <p>
 * The OpenExr package is a pure Java implementation.
 * </p>
 *
 *
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class OpenExr {
    
    /**
     * The magic number, of type int , is always 20000630 (decimal)
     * The first four bytes of an OpenEXR file are always 
     * 0x76, 0x2f, 0x31, 0x01
     * .
     */
    public static final byte[] MAGIC_NUMBER = {0x76, 0x2f, 0x31, 0x01};

    /**
     * Magic number as integer
     */
    public static final int MAGIC_NUMBER_AS_INT = 20000630;
    
    /**
     * The 8 least significant bits(following the magic number), they contain the file format version number
     * .
     */
    protected byte fileFormatVersion;
    
    /**
     * If bit 9 (singleTileBit) is TRUE:
     *      - this is a regular single-part image and the pixels are stored as tiles, 
     *      - and bits 11 (nonImageBit) and 12 (multiPartBit) must be FALSE
     * 
     * If bit 9 is FALSE, and bits 11 (nonImageBit) and 12 (multiPartBit) are also FALSE:
     *      - the data is stored as regular single-part scan line file.
     */
    protected boolean singleTileBit;
    
    /**
     * The maximum length of attribute names, attribute type names and channel names
     * If longNameBit is TRUE, the maximum length is 255 bytes,
     * If longNameBit is FALSE, the maximum length is 31 bytes
     * If bit 11 is 0, all parts are entirely single or multiple
     *   scan line or tiled images.
     */
    protected boolean longNameBit;
    
    /**
     * If bit 11 is 1, there is at least one part which is not a
     *  regular scan line image or regular tiled image (that
     *   is, it is a deep format).
     */
    protected boolean nonImageBit;
    
    /**
     * If bit 12 is 1:
     *   • the file does not contain exactly 1 part and the
     *   'end of header' byte must be included at the
     *   end of each header part, and
     *   • the part number fields must be added to the
     *   chunks
     *   If bit 12 is 0, this is not a multi-part file and the 'end
     *   of header' byte and part number fields in chunks
     *   must be omitted
     *   New in 2.0.
     */
    protected boolean multiPartBit;
    
    /**
     *
     */
    protected List<Map<String,HeaderAttribute<? extends BaseAttributeType>>> attributeList = new ArrayList<>();

    /**
     * Start poit offset table
     */
    protected int offsetStartPosition;

    /**
     * The number of entries in an offset table is defined in one of two ways:
     * 1. If the <code>multiPartBit</code> is set to false and
     * the <code>chunkCount</code> is null, the number of entries in the
     * chunk table is computed using the <code>dataWindow</code>
     * and <code>tileDesc</code> attributes and the compression format.
     * 
     * 2. If the <code>multiPartBit</code> is set, the header must contain
     * a <code>chunkCount</code> attribute.
     */
    protected int countOffsets;
    
    /**
     *
     */
    protected ArrayList<Long> offsetTable = new ArrayList<>();
    
    /**
     *
     */
    protected ArrayList<OpenExrDataChunk> chunkList = new ArrayList<>();
    
    /**
     *
     */
    protected byte[] imgData;
    
    /**
     * Default OpenExr
     */
    protected OpenExr(){
        
    }
    
    /**
     * Return OpenExr image as byte array.
     * @return OpenExr image as byte array 
     * @throws OpenExrAttributeException 
     */    
    public byte[] getRowData() throws OpenExrAttributeException{
        byte[] result = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {

        byteArrayOutputStream.write(getHeaderRowData());

        Long headerSize;
        Long shif;
        
        if(attributeList.size() > 1){
            byteArrayOutputStream.write(OpenExrUtil.NULL_BYTE);
        }
        
        headerSize = new Integer(byteArrayOutputStream.size() + offsetTable.size() * 8 ).longValue();
        shif = headerSize - (long)(countOffsets*8 + offsetStartPosition + 1); 
        
        for (long l : offsetTable) {
            byteArrayOutputStream.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l + shif).array());
        }

        byte [] bb = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.write(getImageRowData());

            result = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {
        }       

        return result;
    }
    
    /**
     *
     * @return
     * @throws IOException
     * @throws OpenExrAttributeException
     */
    private byte[] getHeaderRowData() throws IOException, OpenExrAttributeException{
        int headerSize = 8;
        
        for (Map<String,HeaderAttribute<? extends BaseAttributeType>> attrL : attributeList) {
            for (HeaderAttribute<? extends BaseAttributeType> ha : attrL.values()) {
                headerSize = headerSize + ha.getAttributeSize();
            }
            headerSize = headerSize + 1;
        }
        
        ByteBuffer byteBuffer = ByteBuffer.allocate(headerSize);
        
        byteBuffer.put(MAGIC_NUMBER);
        byteBuffer.put(fileFormatVersion);
        byteBuffer.put(OpenExrUtil.getByteFromBitArray(singleTileBit, longNameBit, nonImageBit, multiPartBit));
        byteBuffer.put(OpenExrUtil.NULL_BYTE);
        byteBuffer.put(OpenExrUtil.NULL_BYTE);

        byteBuffer.array();
        for (Map<String,HeaderAttribute<? extends BaseAttributeType>> attrL : attributeList) {
            for (HeaderAttribute<? extends BaseAttributeType> ha : attrL.values()) {
                byteBuffer.put(ha.getRowData().array());
            }
            byteBuffer.put(OpenExrUtil.NULL_BYTE);
        }
        
        return byteBuffer.array();
    }
    
    /**
     *
     * @return
     */
    private byte[] getImageRowData(){
        return imgData;
    }
    
    /**
     * Save this object as OpenExr image
     * @param fileName
     * @throws FileNotFoundException
     * @throws OpenExrAttributeException
     * @throws IOException
     */
    public void saveToFile(String fileName) throws FileNotFoundException, OpenExrAttributeException, IOException{
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(getRowData());
        out.close();
    }
    
    /**
     * Add new custom attribute to first part header 
     * @param headerAttribute
     * @throws OpenExrAttributeException 
     */
    private void addAttribute(HeaderAttribute headerAttribute) throws OpenExrAttributeException{
        addAttribute(headerAttribute, 0);
    }
    
    /**
     * Add new custom attribute to partNumber part header 
     * @param headerAttribute
     * @param partNumber 
     * @throws OpenExrAttributeException 
     */
    private void addAttribute(HeaderAttribute headerAttribute, int partNumber) throws OpenExrAttributeException{
        attributeList.get(partNumber).put(headerAttribute.getName(), headerAttribute);
    }
    
    /**
     * Add new custom attribute to first part header
     * @param attributeName
     * @param attributeValue
     * @throws OpenExrAttributeException 
     */
    public void addCustomAttribute(String attributeName, String attributeValue) throws OpenExrAttributeException {
        if(StandartAttribute.RESERVED_ATTRIBUTE_NAME.get(attributeName) != null)
            throw  new OpenExrAttributeException(OpenExrAttributeException.RESERVED_WORD);
        
        HeaderAttribute<StringTypeAttribute> headerAttribute = new HeaderAttribute(attributeName,
                new StringTypeAttribute(ByteBuffer.wrap(attributeValue.getBytes(StandardCharsets.UTF_8))));

        this.addAttribute(headerAttribute);
    }
    
    /**
     * 
     * @param headerAttributeName
     * @return 
     */
    public HeaderAttribute getHeaderAttributeByName(String headerAttributeName){
        return getHeaderAttributeByName(headerAttributeName, 0);
    }
    
    /**
     * 
     * @param headerAttributeName
     * @param partNumber
     * @return 
     */
    private HeaderAttribute getHeaderAttributeByName(String headerAttributeName, int partNumber){
        return attributeList.get(partNumber).get(headerAttributeName);
    }
    
    /**
     * 
     * @param attributeName
     * @return 
     */
    public String getCustomAttribute(String attributeName) {
        return ((StringTypeAttribute)attributeList.get(0).get(attributeName).getValue()).getStringValue();
    }

    @Override
    public String toString(){
        return "####OpenEXR Object START\n" +
               "Image Data size: " + imgData.length + "\n" +
               "Magic Number: " + ByteBuffer.wrap(MAGIC_NUMBER).order(ByteOrder.LITTLE_ENDIAN).getInt() + "\n" +
               "File format version: " + fileFormatVersion + "\n" +
               "Flag 9 (singleTileBit): " + singleTileBit + "\n" + 
               "Flag 10 (longNameBit): " + longNameBit + "\n" + 
               "Flag 11 (nonImageBit): " + nonImageBit + "\n" + 
               "Flag 12 (multiPartBit): " + multiPartBit + "\n" +
               "############ Attribute Section START ############\n" +
                attributeListToString(attributeList) + 
               "############# Attribute Section END #############\n" + 
               "Offset start position: " + offsetStartPosition + "\n" + 
               "Count offsets: " + countOffsets + "\n" + 
               "Offset Table" + offsetTable + 
                "\n####OpenEXR Object END \n"
                ;
        

    }
    
    /**
     * 
     * @param attributeList
     * @return 
     */
    private String attributeListToString(List<Map<String, HeaderAttribute<? extends BaseAttributeType>>> attributeList) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        for(Map<String, HeaderAttribute<? extends BaseAttributeType>> attributeL : attributeList) {
            result.append("*********Part number: ").append(count++).append("*********\n");
            for (HeaderAttribute<? extends BaseAttributeType> attribute : attributeL.values()) {
                result.append(attribute);
                result.append("-------------------------------------------------\n");
            }
            result.append("************END PART************\n");
     
        }
        
        return result.toString();
    }
    
}
