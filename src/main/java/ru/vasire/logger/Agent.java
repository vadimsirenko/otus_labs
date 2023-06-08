package ru.vasire.logger;

import org.objectweb.asm.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                return changeMethod(classfileBuffer);
            }
        });
    }

    private static byte[] changeMethod(byte[] originalClass) {
        var cr = new ClassReader(originalClass);
        byte[] finalClass = LoggerClassLoader.logMethods(cr);
        return finalClass;
    }

}