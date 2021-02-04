package com.weimin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    public static void print(Object o){
        System.out.println("【"+Thread.currentThread().getName()+"】"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS").format(new Date())+"====>"+o);
    }
}
