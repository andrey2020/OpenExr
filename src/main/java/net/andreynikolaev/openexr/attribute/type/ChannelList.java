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

import net.andreynikolaev.openexr.OpenExrUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;
import net.andreynikolaev.openexr.attribute.type.ChannelList.Channel;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public final class ChannelList extends TreeMap<String, Channel> implements BaseAttributeType {
    private final String typeName = "chlist";
    
    private ChannelList(){}
    
    public ChannelList(Channel channel){
        this.put(channel.getName(), channel);
    }
    
    public ChannelList(ByteBuffer byteBuffer){
        boolean isChanelListEnd = false;
        while (!isChanelListEnd) {
            Channel ch = new Channel(byteBuffer);                
            this.put(ch.getName(), ch);
            isChanelListEnd = byteBuffer.limit() - byteBuffer.position() == 1;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("[Channel list]\n");
        for (Channel channel : this.values()) {
            result.append("\t________________\n");
            result.append(channel);
            
        }        
        return result.toString();
    }

    @Override
    public ByteBuffer getRowData(){

        ByteBuffer byteBuffer = ByteBuffer.allocate(getValueSize());
             
        for (Channel ch : this.values()){
            byteBuffer.put(ch.getRowData().array());
        }
        byteBuffer.put(OpenExrUtil.NULL_BYTE);
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public int getValueSize() {
        int valueSize = this.size() * Channel.typeSizeInByte + 1;
        for (String channelName: this.keySet()) {
            valueSize = valueSize + channelName.length();
        }
        return valueSize;
    }
    
    /**
     * Channel layout:   
     *       name: zero-terminated string, from 1 to 255 bytes long
     *       pixel type: int, possible values are:
     *                       UINT = 0
     *                       HALF = 1
     *                       FLOAT = 2
     *       pLinear: unsigned char, possible values are 0 and 1
     *       reserved three chars, should be zero
     *       xSampling: int
     *       ySampling: int
     */
    public static final class Channel implements BaseAttributeType{
        private final String typeName = "channel";
        
        public static final int typeSizeInByte = // + name.length() Size of name
                                                1 // NUUL byte 
                                               + OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT // Size of pixel type
                                               + 1 // Size of pLinear
                                               + 3 // Size of reserved three chars
                                               + OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT // Size of xSampling int
                                               + OpenExrUtil.COUNT_BYTE_FOR_INT_OR_FLOAT // Size of ySampling int
                                              // + 1 // NUUL byte 
                                                 ;
        private String name;
        private PixelType pixelType;    
        private boolean pLinear;       
        private int xSampling;
        private int ySampling;
        
        public Channel(ByteBuffer byteBuffer){
            this.name = OpenExrUtil.readNullTerminatedString(byteBuffer);
            this.pixelType = Channel.getPixelTypeByInt(byteBuffer.getInt());
            this.pLinear = byteBuffer.get() == 1;
            byteBuffer.get();
            byteBuffer.get();
            byteBuffer.get();
            xSampling = byteBuffer.getInt();
            ySampling = byteBuffer.getInt();
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PixelType getPixelType() {
            return pixelType;
        }

        public void setPixelType(PixelType pixelType) {
            this.pixelType = pixelType;
        }

        public boolean ispLinear() {
            return pLinear;
        }

        public void setpLinear(boolean pLinear) {
            this.pLinear = pLinear;
        }

        public int getxSampling() {
            return xSampling;
        }

        public void setxSampling(int xSampling) {
            this.xSampling = xSampling;
        }

        public int getySampling() {
            return ySampling;
        }

        public void setySampling(int ySampling) {
            this.ySampling = ySampling;
        }
        
        /**
         *
         * @return
         */
        @Override
        public ByteBuffer getRowData(){
            ByteBuffer byteBuffer = ByteBuffer.allocate(Channel.typeSizeInByte + this.name.length()).order(ByteOrder.LITTLE_ENDIAN);
                byteBuffer.put(name.getBytes(StandardCharsets.UTF_8));
                byteBuffer.put(OpenExrUtil.NULL_BYTE);
                
                byteBuffer.putInt(pixelType.getValue());
                
                byteBuffer.put(pLinear ? (byte) 1 : (byte) 0);
                
                byteBuffer.put(OpenExrUtil.NULL_BYTE);
                byteBuffer.put(OpenExrUtil.NULL_BYTE);
                byteBuffer.put(OpenExrUtil.NULL_BYTE);
                byteBuffer.putInt(xSampling);
                byteBuffer.putInt(ySampling);
                byteBuffer.position(0);

            return byteBuffer;
        }
        
        @Override
        public String toString(){
        return "\t" + "Channel name: " + this.name + "\n" +
               "\t" + "Pixel type: " + this.pixelType.toString() + "\n" +
               "\t" + "pLinear: " + pLinear + "\n" +
               "\t" + "xSampling: " + this.xSampling + "\n" +
               "\t" + "ySampling: " + this.ySampling + "\n"
                ;
        }

        @Override
        public String getTypeName() {
            return this.typeName;
        }

        @Override
        public int getValueSize() {
            return typeSizeInByte + getName().length();
        }
           
        public enum PixelType{

            UNIF(0), HALF(1), FLOAT(2);

            PixelType(int value){
                this.value = value;
            }
            private int value;

            int getValue(){
                return this.value;
            }
        }

        public static PixelType getPixelTypeByInt(int i){
            PixelType result = PixelType.UNIF;
            for (PixelType pt : PixelType.values()) {
                if(pt.getValue() == i) result = pt;
            }
            return result;
        } 
    }
}
