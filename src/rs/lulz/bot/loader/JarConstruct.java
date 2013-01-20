package rs.lulz.bot.loader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import rs.lulz.hierarchy.Constructable;
import rs.lulz.utilities.Constants;
import rs.lulz.utilities.Logging;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarConstruct implements Constructable {

    private final float VERSION = 0.1F;

    private URLClassLoader url;

    protected HashMap<String, ClassNode> loadedClassNodes = new HashMap<String, ClassNode>();

    private String jarPath;

    public JarConstruct(final String jarPath) {
        try {

            //Set up logger and print out the path.
            Logging.log("Path to JAR: " + Constants.JAR_PATH, JarConstruct.class);

            //Creates a new URLClassLoader and loads the JAR.
            url = new URLClassLoader(new URL[]{new URL("file:" + jarPath)});


            //Referencing the JAR's path.
            this.jarPath = jarPath;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public boolean loadClasses() {
        short count = 0;

        try {
            //Startup
            p("---------------------------------------------------------------------");
            p("--------------------      Jar Loader     ----------------------------");
            p("Version: " + VERSION);
            p("File: " + Constants.JAR_PATH);

            p("JC Hash: " + getClass().hashCode());

            //Referencing the JAR file.
            JarFile jf = new JarFile(jarPath);

            //Print out the size of the JarFile
            p("JarFile Size = " + jf.size());
            p("-----------------------------------------------------------------");

            //Referencing the entries.
            Enumeration<? extends JarEntry> en = jf.entries();

            //Looping through the elements (the entries).
            while (en.hasMoreElements()) {

                //The entry to work with.
                JarEntry entry = en.nextElement();

                //Grabbing solely the class files
                if (entry.getClass() != null) {
                    if (entry.getName().endsWith(".class")) {


                        //Count out the entries
                        ++count;

                        //Print out the entry
                        p("Class # " + count + ": " + entry.getName());
                        p("Decompressed File Size: " + entry.getSize() + " bytes" + "\n");

                        //ClassReader retrieves the bytes from a given entry.
                        ClassReader cr = new ClassReader(jf.getInputStream(entry));

                        //Creating a new ClassNode to act as a representative of a class.
                        ClassNode cn = new ClassNode();

                        //Delegating all method calls and data from ClassReader to ClassNode.
                        //Think of it as data from 'cr' are being entrusted or put into 'cn' (such as the class bytes).
                        cr.accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

                        //Put it into the local package's HashMap as a ClassNode.
                        loadedClassNodes.put(cn.name, cn);
                    }
                }

            }

            System.out.println(count + " classes were loaded and stored!");
            p("-----------------------------------------------------------------");
            p("-----------------------------------------------------------------");

            Logging.log("Load successful.", JarConstruct.class);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ClassNode retrieveClass(final String clazz) {
        //Assume that the HashMap
        return loadedClassNodes.get(clazz);
    }

    private void p(Object msg) {
        System.out.println(msg);
    }


    @Override
    public ClassNode queryProduct(final String param) {
        return loadedClassNodes.get(param);
    }

    @Override
    public HashMap queryProduct() {
        return loadedClassNodes;
    }
}
