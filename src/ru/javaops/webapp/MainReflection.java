package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();

        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        field.get(resume);
        System.out.println(resume);
        field.set(resume, "new_uuid");
        System.out.println(resume);

        Method method = resume.getClass().getDeclaredMethod("toString");
        System.out.println(method.getName());
        System.out.println(method.invoke(resume));
    }
}
