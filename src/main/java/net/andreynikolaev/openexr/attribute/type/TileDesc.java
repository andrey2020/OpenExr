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



/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class TileDesc implements BaseAttributeType {
    int xSize;
    int ySize;
    char mode; // mode = levelMode + roundingMode * 16
    
    //Possible values for levelMode
    private static int ONE_LEVEL = 0;
    private static int MIPMAP_LEVELS = 1;
    private static int RIPMAP_LEVELS = 2;
    
    //Possible values for roundingMode:
    private static int ROUND_DOWN = 0;
    private static int ROUND_UP = 1;

    public TileDesc() {
    }

    public TileDesc(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        @Override
    public String getTypeName() {
        return "tiledesc";
    }

    @Override
    public ByteBuffer getRowData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getValueSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
