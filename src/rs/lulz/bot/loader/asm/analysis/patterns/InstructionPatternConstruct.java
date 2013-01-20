package rs.lulz.bot.loader.asm.analysis.patterns;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import rs.lulz.hierarchy.Constructable;

public class InstructionPatternConstruct implements Constructable {


    private String regex, byteCodeText;

    private ClassNode cn;

    private AbstractInsnNode[] insnNodes;

    public InstructionPatternConstruct(final AbstractInsnNode... insnNodes) {
        this.insnNodes = insnNodes;
        this.byteCodeText = null; //figure this out.
    }

    public InstructionPatternConstruct(final String byteCodeText, final String regex) {
        this.regex = regex;
        this.byteCodeText = byteCodeText;
    }

    public int getConfidencePercentage(final InstructionPatternConstruct primeConstruct, final InstructionPatternConstruct... patternConstructs) {
        return 0;
    }


    public String getByteCodeText() {
        return byteCodeText;
    }


    public InstructionPatternConstruct getConstruct() {
        return new InstructionPatternConstruct(byteCodeText, regex);
    }

    public AbstractInsnNode[] toInsnNodeArray(final InstructionPatternConstruct pattern) {
        return null;
    }


    @Override
    public Object queryProduct(String param) {
        throw new NoSuchMethodError("Pointless use of this method in InstructionPatternConstruct!");
    }

    @Override
    public InstructionPatternConstruct queryProduct() {
        return this;
    }
}
