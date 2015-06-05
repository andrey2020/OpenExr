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

import java.util.HashMap;

import net.andreynikolaev.openexr.attribute.type.Box2i;
import net.andreynikolaev.openexr.attribute.type.ChannelList;
import net.andreynikolaev.openexr.attribute.type.Compression;
import net.andreynikolaev.openexr.attribute.type.FloatTypeAttribute;
import net.andreynikolaev.openexr.attribute.type.IntegerTypeAttribute;
import net.andreynikolaev.openexr.attribute.type.LineOrder;
import net.andreynikolaev.openexr.attribute.type.StringTypeAttribute;
import net.andreynikolaev.openexr.attribute.type.TileDesc;
import net.andreynikolaev.openexr.attribute.type.V2f;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class StandartAttribute {
        public static final HashMap<String, Class> RESERVED_ATTRIBUTE_NAME = new HashMap<>();
    
    static{
       
       RESERVED_ATTRIBUTE_NAME.put("channels", ChannelList.class);
       RESERVED_ATTRIBUTE_NAME.put("compression", Compression.class);
       RESERVED_ATTRIBUTE_NAME.put("dataWindow", Box2i.class);
       RESERVED_ATTRIBUTE_NAME.put("displayWindow", Box2i.class);
       RESERVED_ATTRIBUTE_NAME.put("lineOrder", LineOrder.class);
       RESERVED_ATTRIBUTE_NAME.put("pixelAspectRatio", FloatTypeAttribute.class);
       RESERVED_ATTRIBUTE_NAME.put("screenWindowCenter", V2f.class);
       RESERVED_ATTRIBUTE_NAME.put("screenWindowWidth", FloatTypeAttribute.class);
       RESERVED_ATTRIBUTE_NAME.put("tiles", TileDesc.class);
       RESERVED_ATTRIBUTE_NAME.put("view", StringTypeAttribute.class); //Null terminated string
       RESERVED_ATTRIBUTE_NAME.put("name", StringTypeAttribute.class);
       RESERVED_ATTRIBUTE_NAME.put("type", StringTypeAttribute.class);
       RESERVED_ATTRIBUTE_NAME.put("version", IntegerTypeAttribute.class);
       RESERVED_ATTRIBUTE_NAME.put("chunkCount", IntegerTypeAttribute.class);
       RESERVED_ATTRIBUTE_NAME.put("maxSamplesPerPixel", IntegerTypeAttribute.class);
       RESERVED_ATTRIBUTE_NAME.put("version", IntegerTypeAttribute.class);

    }
}
