package com.glen.glengen.util;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-15:35
 * @Description
 */

import lombok.extern.slf4j.Slf4j;

/**
 * @author Glen
 * @create 2020/9/10 15:35
 * @Description
 */
@Slf4j
public class FileOperationUtil {
    /*/**
     * @author Glen
     * @date 2020/9/10 15:35
     * @Description 包名处理
     */
    public static String packageNmae(String packageName) {
        int i;
        int n = packageName.toCharArray().length;
        for (i = 0; i < n; i++) {
            if ((int) '.' == packageName.toCharArray()[i]) {
                if (i == packageName.length() - 1) {
                    packageName = packageName.substring(0, packageName.length() - 1);
                }
            }
        }
        return packageName.replace(".","/");
    }
    /**
      * @author Glen
      * @date 2020/9/10 16:11
      * @Description 类名处理
      */
    public static String className(String className) {
        char[] n = className.toCharArray();
        if (n[0] >= 'A' && n[0] <= 'Z'){
            className = className + ".java";
        }else {
            className = String.valueOf(n[0]).toUpperCase()+className.substring(1,className.length())+".java";
        }
        log.info("className:"+className);
        return className;
    }
}
