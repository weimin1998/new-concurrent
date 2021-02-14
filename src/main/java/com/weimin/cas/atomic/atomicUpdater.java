package com.weimin.cas.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子更新器
 */
public class atomicUpdater {
    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        teacher.setName("weimin");
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater =
                // 为哪个类的哪个属性创建更新器
                AtomicReferenceFieldUpdater.newUpdater(Teacher.class,String.class,"name");

        atomicReferenceFieldUpdater.compareAndSet(teacher,"weimin","魏敏");

        System.out.println(teacher);
    }
}

class Teacher{
    // 不能private
    // 必须volatile
     volatile String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                '}';
    }
}