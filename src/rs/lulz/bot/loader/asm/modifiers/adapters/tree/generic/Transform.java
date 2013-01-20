package rs.lulz.bot.loader.asm.modifiers.adapters.tree.generic;

import org.objectweb.asm.tree.ClassNode;
import rs.lulz.utilities.Filter;

public abstract class Transform implements Filter<ClassNode> {

    public abstract boolean accept(ClassNode theClass);

    public abstract void process(ClassNode cNode);

    public abstract void runTransform();

}
