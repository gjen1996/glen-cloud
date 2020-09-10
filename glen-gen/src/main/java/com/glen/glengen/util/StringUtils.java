package com.glen.glengen.util;

/**
 * @author 盖玉成
 * @date 2020/9/10 23:49
 * @description 该类的作用
 */
public class StringUtils {

    public static int kmp(String str, String dest,int[] next){//str文本串  dest 模式串
        for(int i = 0, j = 0; i < str.length(); i++){
            while(j > 0 && str.charAt(i) != dest.charAt(j)){
                j = next[j - 1];
            }
            if(str.charAt(i) == dest.charAt(j)){
                j++;
            }
            if(j == dest.length()){
                return i-j+1;
            }
        }
        return 0;
    }
    public static int[] kmpnext(String dest){
        int[] next = new int[dest.length()];
        next[0] = 0;
        for(int i = 1,j = 0; i < dest.length(); i++){
            while(j > 0 && dest.charAt(j) != dest.charAt(i)){
                j = next[j - 1];
            }
            if(dest.charAt(i) == dest.charAt(j)){
                j++;
            }
            next[i] = j;
        }
        return next;
    }
    public static void main(String[] args){
        String a = "ab";
        String b = "ssdfgasdbababab";
        int[] next = kmpnext(a);
        int res = kmp(b, a,next);
        System.out.println(res);
        for(int i = 0; i < next.length; i++){
            System.out.println("1----"+next[i]);
        }
        System.out.println("2----"+next.length);
    }

    public static String replaceString(String re ,String str){
        String result = null;

        return result;
    }
}
