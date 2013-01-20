package rs.lulz.bot.loader;

import org.objectweb.asm.tree.ClassNode;
import rs.lulz.bot.loader.asm.modifiers.adapters.tree.generic.AbstractClassTransform;
import rs.lulz.bot.loader.commons.transformations.ClientTransform;
import rs.lulz.utilities.Logging;
import rs.lulz.utilities.UpdaterConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class Updater {

    private static rs.lulz.bot.loader.JarConstruct jc;

    private static volatile ArrayList<AbstractClassTransform> transforms = new ArrayList<AbstractClassTransform>();

    protected volatile HashMap<String, ClassNode> injClasses = new HashMap<String, ClassNode>();

    private int flags = UpdaterConfiguration.ADAPT;

    public Updater(final String path, final int flags) {
        this.flags = flags;

        if ((flags & UpdaterConfiguration.ADAPT) != 0) {

            //Startup the JarConstruct
            jc = new JarConstruct(path);

            //Load all of the classes and store it into a HashMap
            jc.loadClasses();


        }

        //TODO: Add the transformation classes
        transforms.add(new ClientTransform());

        //Run the transforms
        runTransforms();

    }

    protected JarConstruct getJar() {
        return jc;
    }


    private void runTransforms() {

        //Loop through the AbstractClassTransforms.
        for (AbstractClassTransform act : transforms) {

            //Loop through the classes.
            for (ClassNode cn : getJar().loadedClassNodes.values()) {

                //Allow a ClassNode to be processed IF the Transformation class filter accepts it.
                if (act.accept(cn)) {
                    Logging.log(cn.name + " was accepted", Updater.class);

                    //Process the transform.
                    act.process(cn);

                    //Run the event transformations.
                    act.runTransform();

                    //Produce the events.
                    act.applyChanges();

                    //Get the altered ClassNode
                    ClassNode c = act.queryProduct();

                    //Store it onto a HashMap for the injector's use.
                    injClasses.put(c.name, c);
                }
            }
        }
    }

}
