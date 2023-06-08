package ru.vasire.logger;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import ru.vasire.annotation.Log;

import java.util.Arrays;
import java.util.List;

public class LoggerMethodAdapter extends AdviceAdapter {
    private static final String ANNOTATION_DESCRIPTOR_LOG = Type.getType(Log.class).getDescriptor();
    String name;
    boolean logged = false;
    List<Type> paramTypes;
    boolean isStatic;

    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param api           the ASM API version implemented by this visitor. Must be one of the {@code
     *                      ASM}<i>x</i> values in {@link Opcodes}.
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected LoggerMethodAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
        this.name = name;
        this.isStatic = (access & Opcodes.ACC_STATIC) != 0;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if(descriptor.equalsIgnoreCase(ANNOTATION_DESCRIPTOR_LOG)){
            this.logged = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    protected void onMethodEnter() {
        if(logged) {
            var t = getArgumentTypes();
           paramTypes = Arrays.stream(getArgumentTypes()).toList();
            try {
                trace("enter");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onMethodEnter();
    }

    @Override
    public void visitParameter(String name, int access) {
        super.visitParameter(name, access);
    }

    private void trace(String action) throws ClassNotFoundException {
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("executed method: " + name );
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);

        int offset = (isStatic)? 0 : 1;
        for (int i = 0; i < paramTypes.size(); i++) {

            if(i>0) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn(", ");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            } else{
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn(" parameters" + Arrays.toString(paramTypes.stream().map(t->t.getClassName()).toArray())+": ");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            }
            String desc = "(" + paramTypes.get(i).toString() + ")V";
            desc = getDescription(paramTypes.get(i));
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(getReturnOpcode(paramTypes.get(i)), i+offset);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", desc, false);
        }
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "()V", false);
        mv.visitEnd();
    }

    public static int getReturnOpcode(Type type) {
        if (type != null) {
            if(type.getClassName().equals("java.lang.String"))
                return Opcodes.ALOAD;
            switch (type.getSort()) {
                case Type.VOID:
                    System.out.println("type can't be void");
                    return -1;
                case Type.BOOLEAN:
                case Type.CHAR:
                case Type.BYTE:
                case Type.SHORT:
                case Type.INT:
                    return Opcodes.ILOAD;
                case Type.FLOAT:
                    return Opcodes.FLOAD;
                case Type.LONG:
                    return Opcodes.LLOAD;
                case Type.DOUBLE:
                    return Opcodes.DLOAD;
                default:
                    // Type.ARRAY:
                    // Type.OBJECT:
                    return Opcodes.ALOAD;
            }
        } else {
            System.out.println("type is null!");
            return -1;
        }
    }

    public static String getDescription(Type type) {
        if (type != null) {
            if(type.getClassName().equals("java.lang.String"))
                return "(Ljava/lang/Object;)V";
            switch (type.getSort()) {
                case Type.VOID:
                    System.out.println("type can't be void");
                    return "()V";
                case Type.BOOLEAN:
                case Type.CHAR:
                case Type.BYTE:
                case Type.SHORT:
                case Type.INT:
                case Type.FLOAT:
                case Type.LONG:
                case Type.DOUBLE:
                    return "(" + type + ")V";
                default:
                    // Type.ARRAY:
                    // Type.OBJECT:
                    return "(Ljava/lang/Object;)V";
            }
        } else {
            System.out.println("type is null!");
            return "()V";
        }
    }
}
