package com.weimin.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe
 */
public class Get {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);

        // 偏移量
        long id = unsafe.objectFieldOffset(Student.class.getDeclaredField("id"));
        long name = unsafe.objectFieldOffset(Student.class.getDeclaredField("name"));

        System.out.println(id);
        System.out.println(name);
        Student student = new Student();

        unsafe.compareAndSwapObject(student,id,null,"1");
        unsafe.compareAndSwapObject(student,name,null,"weimin");

        System.out.println(student);

    }
}
