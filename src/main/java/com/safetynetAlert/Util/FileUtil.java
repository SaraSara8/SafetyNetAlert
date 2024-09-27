package com.safetynetAlert.Util;

import java.io.IOException;


import com.jsoniter.any.Any;


public interface FileUtil {
    byte[] readFile(String filePath) throws IOException;
    
    Boolean writeFile(String filePath,  Object dataMap) throws IOException;

    Any deserializeFile(byte[] byteData);


}
