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
public final class V3f implements BaseAttributeType {
    private final String typeName = "v3f";
    private float a;
    private float b;
    private float c;

    public V3f() {
    }

    public V3f(ByteBuffer byteBuffer) {
        this.a = byteBuffer.getFloat();
        this.b = byteBuffer.getFloat();
        this.b = byteBuffer.getFloat();
    }

    @Override
    public String toString() {
        return "x=" + a + ", " +
        "b=" + b + ", " + 
        "c=" + c + ", "
        ;
    }
    
    @Override
    public String getTypeName() {
        return typeName;
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
