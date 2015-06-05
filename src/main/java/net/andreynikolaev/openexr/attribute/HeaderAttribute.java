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
package net.andreynikolaev.openexr.attribute;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import net.andreynikolaev.openexr.OpenExrUtil;
import net.andreynikolaev.openexr.attribute.type.BaseAttributeType;
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;


/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 * @param <T implements BaseAttributeType>
 */
public class HeaderAttribute<T extends BaseAttributeType> {
    private String name;
    private T value;
    
    public HeaderAttribute(String name, T value) throws OpenExrAttributeException{
        if (name == null || value == null) throw new OpenExrAttributeException(OpenExrAttributeException.NULL_ATTRIBUTE_NAME);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws OpenExrAttributeException {
        if (name == null) throw new OpenExrAttributeException(OpenExrAttributeException.NULL_ATTRIBUTE_NAME);
        this.name = name;
    }

    public int getAttributeSize(){
        return name.length() + 1 + value.getTypeName().length() + 1 + 4 + value.getValueSize();
    }
    
    public int getValueSize(){
        return value.getValueSize();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) throws OpenExrAttributeException{
        if (value == null) throw new OpenExrAttributeException(OpenExrAttributeException.NULL_ATTRIBUTE_NAME);
        this.value = value;
    }
    
    @Override
    public String toString(){
        String result = "Name: " + name + "\n" + 
                        "Type: " + value.getTypeName() + "\n" +
                        "Size: " + value.getValueSize() + "\n" +
                        "Value: " + getValue() + "\n";
        return result;
    }
    
    public ByteBuffer getRowData(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(getAttributeSize()).order(ByteOrder.LITTLE_ENDIAN);
        
        byteBuffer.put(name.getBytes());
        byteBuffer.put(OpenExrUtil.NULL_BYTE);
        byteBuffer.put(value.getTypeName().getBytes());
        byteBuffer.put(OpenExrUtil.NULL_BYTE); 
        byteBuffer.putInt(getValueSize());
        byteBuffer.put(value.getRowData().array());

        return byteBuffer;
    }
    
     
    @Override
    public int hashCode(){
        return name.hashCode() + value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;        
        if (getClass() != obj.getClass()) return false;        
        final HeaderAttribute<?> other = (HeaderAttribute<?>) obj;
        if (!Objects.equals(this.name, other.name)) return false;        
        if (!Objects.equals(this.value, other.value)) return false;
        
        return true;
    }

    
}
