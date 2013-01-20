package rs.lulz.bot.loader.commons.transformations;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ClassGen;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import rs.lulz.bot.loader.asm.modifiers.adapters.tree.generic.AbstractClassTransform;
import rs.lulz.utilities.Logging;
import rs.lulz.utilities.Variables;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ClientTransform extends AbstractClassTransform {

    @Override
    public boolean accept(ClassNode theClass) {
        Logging.log("Finding client.class", ClientTransform.class);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        theClass.accept(cw);

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(cw.toByteArray()));
        ClassParser cp = new ClassParser(dis, theClass.name);

        int utfIdx = 0;

        try {
            JavaClass jc = cp.parse();
            ClassGen cg = new ClassGen(jc);
            utfIdx = cg.getConstantPool().lookupUtf8("Welcome to Project RS06");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Logging.log("Class: " + theClass.name, ClientTransform.class);
        return theClass.name.equals(Variables.mainClass);
    }

    @Override
    public void runTransform() {
        addInterface("rs/lulz/hooks/Client");
        addGetter("BD", Type.getType(String.class).getDescriptor(), "getPasswordOnLogin", ALOAD, ARETURN);
    }

    public Object queryProduct(String param) {
        return super.queryProduct();
    }
}
