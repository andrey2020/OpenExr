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
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;


/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public enum LineOrder implements BaseAttributeType {
    
    INCREASING_Y {
        private final int typeNumber = 0;
        @Override
        public int getLineOrderValue() {
            return typeNumber;
        }
    },
    DECREASING_Y {
        private final int typeNumber = 1;
        @Override
        public int getLineOrderValue() {
            return typeNumber;
        }
    },
    RANDOM_Y {
        private final int typeNumber = 2;
        @Override
        public int getLineOrderValue() {
            return typeNumber;
        }
    };
    

    @Override
    public String toString() {
       return "" + this.getLineOrderValue() + " " + this.name();
    }
        @Override
    public String getTypeName() {
        return "lineOrder";
    }

    @Override
    public ByteBuffer getRowData() {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(1)
                .put((byte)this.getLineOrderValue());
        byteBuffer.position(0);
        return byteBuffer;
    }

    @Override
    public int getValueSize() {
        return 1;
    }
    
    public static LineOrder getByNumber(ByteBuffer byteBuffer) throws OpenExrAttributeException {
        int type = byteBuffer.get();
        switch (type){
            case 0: return INCREASING_Y;
            case 1: return DECREASING_Y;
            case 2: return RANDOM_Y;
            default: throw new OpenExrAttributeException(OpenExrAttributeException.LINEORDER_UNKNOWN_TYPE, type);
        }
        
    }
    
    public abstract int getLineOrderValue();
    
}
