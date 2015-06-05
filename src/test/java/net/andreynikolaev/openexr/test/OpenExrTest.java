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
package net.andreynikolaev.openexr.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import net.andreynikolaev.openexr.OpenExr;
import net.andreynikolaev.openexr.OpenExrBuilder;
import net.andreynikolaev.openexr.OpenExrUtil;
import net.andreynikolaev.openexr.exceptions.OpenExrAttributeException;
import net.andreynikolaev.openexr.exceptions.OpenExrException;
import net.andreynikolaev.openexr.exceptions.OpenExrFormatException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author <a href="mailto:ich@andrey-nikolaev.net">Andrey Nikolaev</a>
 */
public class OpenExrTest {
    private static final ArrayList<String> fileToTestList = new ArrayList<>();
    
    static{
        for (int i = 11; i <= 11; i++) {
            fileToTestList.add("testImg/demo"+i+".exr");
        } 
    }
    
    @Test
    public void reWriteWithoutChanges_OpenExrTest_Success() throws NoSuchAlgorithmException{        
        
        for (String fileNameOriginal : fileToTestList) {
            try {
             
                //Create new OpenExr object from file
                OpenExr openExr = OpenExrBuilder.readAllData(Files.readAllBytes(Paths.get(fileNameOriginal)));
                System.out.println(openExr);
                byte[] openExrAsArray = openExr.getRowData();
                OpenExr openExrNew = OpenExrBuilder.readAllData(openExrAsArray);
                //MD5 checker
                assertEquals(getCheckSumFromByteArray(openExrAsArray), getCheckSumFromByteArray(openExrNew.getRowData()));
            } catch (OpenExrFormatException | OpenExrAttributeException | IOException ex) {
                System.err.println(ex);
            }
        }        
    }
        
    //@Test
    public void customAttributeReadWriteTest_Success() throws OpenExrFormatException, OpenExrAttributeException, IOException{
        for (String fileNameOriginal : fileToTestList) {
            
            String testName = "_FROM_TEST_READ_WRITE_TEST";
            String fileNameNew = "testImg/output/OUTPUT" + testName + fileNameOriginal.split("/")[1];
            String attributeName = "                            OrderID";
            String attributeValue = "test_Attribute value \n Id 123 üß фб";            
            
            //Create new OpenExr object from file
            OpenExr openExr = OpenExrBuilder.readAllData(Files.readAllBytes(Paths.get(fileNameOriginal)));
            
            //Add new attribute
            openExr.addCustomAttribute(attributeName, attributeValue);
            
            //Save OpenExr object in new file
            openExr.saveToFile(fileNameNew);            
            
            //Get attribute value from new file
            OpenExr n = OpenExrBuilder.readAllData(Files.readAllBytes(Paths.get(fileNameNew)));           

            
            String orderId = n.getCustomAttribute(attributeName);            
   
            //Attribute checker
            assertEquals(attributeValue, orderId);
            
        }        
    }
    
    //@Test
    public void customAttributeWriteTest_Failure() throws IOException, OpenExrException{
        for (String fileNameOriginal : fileToTestList) {
            
           // BufferedImage bi = new BufferedImage(null, new WritableRaster(), true, null)
            String attributeName = "compression"; // reserved word
            String attributeValue = "test_Order Id 123 üß";            
            
            //Create new OpenExr object from file
            OpenExr openExr = OpenExrBuilder.readAllData(Files.readAllBytes(Paths.get(fileNameOriginal)));
            String exceptionMessage = "";
            //Try create new attribute with reserved word
            try {                
                openExr.addCustomAttribute(attributeName, attributeValue);
            } catch (OpenExrAttributeException ex) {
                exceptionMessage = ex.getMessage();
            }
            
            //Exception message checker
            assertEquals(OpenExrAttributeException.RESERVED_WORD, exceptionMessage);
            
        }        
    }
    
    private String getCheckSumFromByteArray(byte[] rowData) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(rowData);
        return OpenExrUtil.toHexString(md.digest());
    }
}
