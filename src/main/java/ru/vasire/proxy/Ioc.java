package ru.vasire.proxy;

import ru.vasire.annotation.Log;
import ru.vasire.model.Person;
import ru.vasire.model.PersonInterface;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ioc {

    private Ioc() {
    }

    public static PersonInterface createPersonClass() {
        InvocationHandler handler = new PersonInvocationHandler(new Person());
        return (PersonInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{PersonInterface.class}, handler);
    }

    static class PersonInvocationHandler implements InvocationHandler {
        private final PersonInterface personClass;

        PersonInvocationHandler(PersonInterface personClass) {
            this.personClass = personClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method methodOfInstance = personClass.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

            if(methodOfInstance.getAnnotation(Log.class)!= null) {
                System.out.print("executed method: " + method.getName());
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        System.out.print(String.format(", Param[%s]: ", i) + args[i]);
                    }
                }
                System.out.println();
            }
            return method.invoke(personClass, args);
        }
    }
}