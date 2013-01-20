package rs.lulz.bot.loader.asm.modifiers.adapters.tree;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class AddMethodAdapter extends ClassVisitor implements Opcodes {

    private String fieldName = null;
    private String getterName = null;
    private String fieldDescriptor = null;
    private String signature = null;
    private String targetClazz = null;

    private boolean isFieldPresent = false;
    private boolean isMethodPresent = false;

    private ClassVisitor next;

    private int varInsn, retInsn;

    public AddMethodAdapter(final ClassVisitor cv, final String fieldName, final String fieldDescriptor, final String getterName, final int varInsn, final int retInsn) {
        super(ASM4, new ClassNode());
        next = cv;

        this.fieldName = fieldName;
        this.getterName = getterName;
        this.varInsn = varInsn;
        this.retInsn = retInsn;
        this.fieldDescriptor = fieldDescriptor;
    }

    @Override
    public void visitEnd() {
        ClassNode cn = (ClassNode) cv;

        for (FieldNode f : cn.fields) {
            if (fieldName.equals(f.name) && fieldDescriptor.equals(f.desc)) {
                isFieldPresent = true;
                signature = f.signature;
                break;
            }
        }

        for (MethodNode mv : cn.methods) {
            if (getterName.equals(cn.name) && fieldDescriptor.equals(mv.desc)) {
                isMethodPresent = true;
                break;
            }
        }

        if (isFieldPresent && !isMethodPresent) {
            MethodNode mn = new MethodNode(ACC_PUBLIC, getterName, "()" + fieldDescriptor, signature, null);

            mn.instructions.add(new VarInsnNode(varInsn, 0));
            mn.instructions.add(new FieldInsnNode(GETFIELD, cn.name, fieldName, fieldDescriptor));
            mn.instructions.add(new InsnNode(retInsn));

            cn.methods.add(mn);
            System.out.println("[+Method Addition] " + fieldDescriptor + " " + getterName + "() returns " + fieldName);
        }


        try {
            cn.accept(next);
        } catch (Exception ez) {
            ez.printStackTrace();
        }

    }

}
