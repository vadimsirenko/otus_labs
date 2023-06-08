package ru.vasire.logger;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LoggerClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            ClassReader cr = new ClassReader(className);
            byte[] bytes = logMethods(cr);

//            try (OutputStream fos = new FileOutputStream("proxyASM.class")) {
//                fos.write(bytes);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return defineClass(className, bytes, 0, bytes.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadClass(className);
    }

    public static byte[] logMethods(ClassReader cr){
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new LoggerClassVisitor(Opcodes.ASM7, cw);

        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }
}
