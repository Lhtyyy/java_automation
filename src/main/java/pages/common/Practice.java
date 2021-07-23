package pages.common;

import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Practice {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(Class.forName("org.openqa.selenium.By"));

        Method[] methods = By.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        Method method = By.class.getDeclaredMethod("id", String.class);
        Object object = method.invoke(By.class, "id");
        System.out.println(object);
    }
}
