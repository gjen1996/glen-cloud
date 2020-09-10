package com.glen.glengen.service;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-14:22
 * @Description
 */

import com.glen.glencommonsystem.util.R;

import java.io.File;
import java.io.IOException;

/**
 * @author Glen
 * @create 2020/9/10 14:22 
 * @Description
 */
public interface CopyDirOperService {
    //复制文件
    R copyFile(File sourceFile, File targetFile) throws IOException;
    //复制文件夹
    R copyDirectiory(String sourceDir, String targetDir) throws IOException;
}
