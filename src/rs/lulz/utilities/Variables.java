package rs.lulz.utilities;

import java.applet.Applet;
import java.util.HashMap;

public class Variables {

    public static String mainClass = "";
    public static Applet applet;
    public static HashMap<String, String> params = new HashMap<String, String>();
    public static ClassLoader loader;

    public static void setApplet(Applet a) {
        applet = a;
    }

    public static Applet getApplet() {
        return applet;
    }

    public static HashMap<String, String> getParameters() {
        return params;
    }

    public static void setClassLoader(final ClassLoader urlc) {
        loader = urlc;
    }

    public static ClassLoader getClassLoader() {
        return loader;
    }

}
