package com.track.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class uploadAction  
{  
    public static String upload(MultipartFile myfile,String realPath) throws IOException  
    {    
    	String uuid=UUID.randomUUID().toString().replaceAll("-", "");
	    String fileName = myfile.getOriginalFilename();
	    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
	    String finaName = uuid+"."+suffix;
    	FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, finaName));     
        return finaName;  
    }  
}  