package ru.vasire.logger;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class LoggerClassVisitor extends ClassVisitor {

    protected LoggerClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        var methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new LoggerMethodAdapter(api, methodVisitor, access, name, descriptor);
    }
}
