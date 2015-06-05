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
public enum Compression implements BaseAttributeType{
    
    
    NO_COMPRESSION {
        private final int typeNumber = 0;
        private final int regularNumberScanLinePerBlock = 1;
        private final int deepNumberScanLinePerBlock = 1;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    },
    RLE_COMPRESSION {
        private final int typeNumber = 1;
        private final int regularNumberScanLinePerBlock = 1;
        private final int deepNumberScanLinePerBlock = 1;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    },
    ZIPS_COMPRESSION {
        private final int typeNumber = 2;
        private final int regularNumberScanLinePerBlock = 1;
        private final int deepNumberScanLinePerBlock = 1;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    },
    ZIP_COMPRESSION {
        private final int typeNumber = 3;
        private final int regularNumberScanLinePerBlock = 16;
        private final int deepNumberScanLinePerBlock = 16;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    },
    PIZ_COMPRESSION {
        private final int typeNumber = 4;
        private final int regularNumberScanLinePerBlock = 32;
        private final int deepNumberScanLinePerBlock = -1;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    },
    PXR24_COMPRESSION {
        private final int typeNumber = 5;
        private final int regularNumberScanLinePerBlock = 16;
        private final int deepNumberScanLinePerBlock = -1;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    },
    B44_COMPRESSION {
        private final int typeNumber = 6;
        private final int regularNumberScanLinePerBlock = 32;
        private final int deepNumberScanLinePerBlock = -1;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    },
    B44A_COMPRESSION {
        private final int typeNumber = 7;
        private final int regularNumberScanLinePerBlock = 32;
        private final int deepNumberScanLinePerBlock = -1;

        @Override
        public int getCommpressionValue() {
            return typeNumber;
        }

        @Override
        public int getRegularNumberScanLinePerBlock() {
            return regularNumberScanLinePerBlock;
        }

        @Override
        public int getDeepNumberScanLinePerBlock() {
            return deepNumberScanLinePerBlock;
        }
    };
    
    @Override
    public ByteBuffer getRowData(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1).put((byte)this.getCommpressionValue());
        byteBuffer.position(0);
        return byteBuffer;
       
    };

    @Override
    public int getValueSize(){
        return 1;
    }

    @Override
    public String getTypeName(){
        return "compression";
    }
    
    @Override
    public String toString() {
        return "" + this.getCommpressionValue() + " " + this.name();
    }    
     
    public static Compression getByNumber(ByteBuffer byteBuffer) throws OpenExrAttributeException {
        int type = byteBuffer.get();
        switch (type){
            case 0: return NO_COMPRESSION;
            case 1: return RLE_COMPRESSION;
            case 2: return ZIPS_COMPRESSION;
            case 3: return ZIP_COMPRESSION;
            case 4: return PIZ_COMPRESSION;
            case 5: return PXR24_COMPRESSION;
            case 6: return B44_COMPRESSION;
            case 7: return B44A_COMPRESSION;
            default: throw new OpenExrAttributeException(OpenExrAttributeException.COMPRESSION_UNKNOWN_TYPE, type);
        }
        
    }
    
    public abstract int getCommpressionValue();
    
    public abstract int getRegularNumberScanLinePerBlock();
    
    public abstract int getDeepNumberScanLinePerBlock();
    
}
