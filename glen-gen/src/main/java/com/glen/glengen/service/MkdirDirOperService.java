package com.glen.glengen.service;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-14:39
 * @Description
 */

import com.glen.glencommonsystem.util.R;

/**
 * @author Glen
 * @create 2020/9/10 14:39 
 * @Description
 */
public interface MkdirDirOperService {
    //创建文件
    R CreateFile(String directory,String destFileName);
    //创建目录
    R createDir(String destDirName);
}
