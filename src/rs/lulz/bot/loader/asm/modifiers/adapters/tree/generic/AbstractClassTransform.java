package rs.lulz.bot.loader.asm.modifiers.adapters.tree.generic;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import rs.lulz.bot.loader.asm.modifiers.adapters.tree.AddInterfaceAdapter;
import rs.lulz.bot.loader.asm.modifiers.adapters.tree.AddMethodAdapter;
import rs.lulz.hierarchy.Constructable;

public abstract class AbstractClassTransform extends Transform implements Opcodes, Constructable {

    private ClassReader cr;
    private ClassWriter cw;

    private ClassVisitor currentAdapter;

    private byte[] classBytes;


    /**
     * Starts up the transform.
     *
     * @param cn - The class to use.
     */
    public void process(final ClassNode cn) {

        try {
            //Set up the ClassWriter.
            cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);


            //Delegate cn method calls and data to 'cw'.
            cn.accept(cw);

            //Set up the ClassReader
            cr = new ClassReader(cw.toByteArray());

            //Set the current adapter to be the ClassWriter.
            currentAdapter = cw;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * End-User could use this to manipulate files.
     */
    public abstract void runTransform();

    /**
     * Adds a getter to return a wanted value.
     *
     * @param targetVar  - The target field name (to be changed so that it auto detects the field!).
     * @param descriptor - The descriptor of the target field.
     * @param getterName - The name of the getter method.
     * @param varInsn    - The variable instantiation instruction.
     * @param retInsn    - The return instruction (which returns the value of what you want).
     * @since 1.7
     */
    public void addGetter(final String targetVar, final String descriptor, final String getterName, final int varInsn, final int retInsn) {
        ClassVisitor addMethodAdapter = new AddMethodAdapter(currentAdapter, targetVar, descriptor, getterName, varInsn, retInsn);
        currentAdapter = addMethodAdapter;
    }

    /**
     * Adds an interface to a given class.
     *
     * @param interfacesToAdd - The interfaces to add to the given class.
     */
    public void addInterface(final String... interfacesToAdd) {
        AddInterfaceAdapter ai = new AddInterfaceAdapter(currentAdapter, interfacesToAdd);
        currentAdapter = ai;
    }


    public void applyChanges() {
        //Apply transformations from the top of the hierarchy backwards.
        cr.accept(currentAdapter, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

        //Have the new class bytes ready to go.
        classBytes = cw.toByteArray();
    }

    @Override
    public ClassNode queryProduct() {
        if (classBytes != null) {
            ClassReader cr = new ClassReader(classBytes);
            ClassNode cn = new ClassNode(ASM4);
            cr.accept(cn, 0);
            return cn;
        } else {
            throw new NullPointerException("ClassNode bytes is null!");
        }
    }


}
