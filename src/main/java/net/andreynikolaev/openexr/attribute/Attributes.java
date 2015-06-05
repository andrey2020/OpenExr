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

import java.nio.ByteBuffer;
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
import net.andreynikolaev.openexr.attribute.type.StringTypeAttribute;
import net.andreynikolaev.openexr.attribute.type.TileDesc;
import net.andreynikolaev.openexr.attribute.type.TimeCode;
import net.andreynikolaev.openexr.attribute.type.UnknownAttributeType;
import net.andreynikolaev.openexr.attribute.type.V2f;
import net.andreynikolaev.openexr.attribute.type.V2i;
import net.andreynikolaev.openexr.attribute.type.V3f;
import net.andreynikolaev.openexr.attribute.type.V3i;
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public enum Attributes {
    
    
    BOX2I {

        @Override
        public HeaderAttribute<Box2i> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new Box2i(byteBuffer));
        }
    },
    BOX2F {

        @Override
        public HeaderAttribute<Box2f> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new Box2f(byteBuffer));
        }
    },
    CHLIST {

        @Override
        public HeaderAttribute<ChannelList> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new ChannelList(byteBuffer));
        }
    },
    CHROMATICITIES {

        @Override
        public HeaderAttribute<Chromaticities> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new Chromaticities(byteBuffer));
        }
    } ,
    COMPRESSION {

        @Override
        public HeaderAttribute<Compression> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, Compression.getByNumber(byteBuffer));
        }
    },
    DOUBLE {

        @Override
        public HeaderAttribute<DoubleTypeAttribute> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new DoubleTypeAttribute(byteBuffer));
        }
    } ,
    ENVMAP {

        @Override
        public HeaderAttribute<Envmap> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, Envmap.getByNumber(byteBuffer));
        }
    } ,
    FLOAT {

        @Override
        public HeaderAttribute<FloatTypeAttribute> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new FloatTypeAttribute(byteBuffer));
        }
    } ,
    INT {

        @Override
        public HeaderAttribute<IntegerTypeAttribute> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new IntegerTypeAttribute(byteBuffer));
        }
    },
    KEYCODE {

        @Override
        public HeaderAttribute<Keycode> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new Keycode(byteBuffer));
        }
    } ,
    LINEORDER {

        @Override
        public HeaderAttribute<LineOrder> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, LineOrder.getByNumber(byteBuffer));
        }
    } ,
    M33F {

        @Override
        public HeaderAttribute<M33f> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new M33f(byteBuffer));
        }
    },
    M44F {

        @Override
        public HeaderAttribute<M44f> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new M44f(byteBuffer));
        }
    } ,
    PREVIEW {

        @Override
        public HeaderAttribute<Preview> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new Preview(byteBuffer));
        }
    },
    STRING {

        @Override
        public HeaderAttribute<StringTypeAttribute> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new StringTypeAttribute(byteBuffer));
        }
    } ,
    TILEDESC {

        @Override
        public HeaderAttribute<TileDesc> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new TileDesc(byteBuffer));
        }
    } ,
    TIMECODE {

        @Override
        public HeaderAttribute<TimeCode> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new TimeCode(byteBuffer));
        }
    } ,
    V2I {

        @Override
        public HeaderAttribute<V2i> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new V2i(byteBuffer));
        }
    },
    V2F {

        @Override
        public HeaderAttribute<V2f> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new V2f(byteBuffer));
        }
    } ,
    V3I {

        @Override
        public HeaderAttribute<V3i> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new V3i(byteBuffer));
        }
    } ,
    V3F {

        @Override
        public HeaderAttribute<V3f> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new V3f(byteBuffer));
        }
    },
    UNKNOWN{

        @Override
        public HeaderAttribute<UnknownAttributeType> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException {
            return new HeaderAttribute<>(attributeName, new UnknownAttributeType(byteBuffer));
        }
        
    };
    
    public abstract HeaderAttribute<? extends BaseAttributeType> getNewInstance(String attributeName, ByteBuffer byteBuffer) throws OpenExrAttributeException;
    
}
