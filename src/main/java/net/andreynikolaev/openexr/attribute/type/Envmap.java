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
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;



/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public enum Envmap implements BaseAttributeType {
    ENVMAP_LATLONG {

        @Override
        public int getEnvmapValue() {
            return 0;
        }
    },
    ENVMAP_CUBE {

        @Override
        public int getEnvmapValue() {
            return 1;
        }
    };
    
    public static Envmap getByNumber(ByteBuffer byteBuffer) throws OpenExrAttributeException {
        int type = byteBuffer.get();
        switch (type){
            case 0: return ENVMAP_LATLONG;
            case 1: return ENVMAP_CUBE;
            default: throw new OpenExrAttributeException(OpenExrAttributeException.ENVMAP_UNKNOWN_TYPE, type);
        }
        
    }
    
    @Override
    public ByteBuffer getRowData(){
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(1).order(ByteOrder.LITTLE_ENDIAN)
                .put((byte)this.getEnvmapValue());
        byteBuffer.position(0);
        return byteBuffer;       
    };

    @Override
    public int getValueSize(){
        return 1;
    }

    @Override
    public String getTypeName(){
        return "envmap";
    }
    
    @Override
    public String toString() {
        return "" + this.getEnvmapValue() + " " + this.name();
    } 
    
    public abstract int getEnvmapValue();
    
}
