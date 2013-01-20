package rs.lulz;

import rs.lulz.utilities.Constants;
import rs.lulz.utilities.Logging;
import rs.lulz.utilities.Variables;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class GUI extends JFrame implements AppletStub {

    private static final long serialVersionUID = 1L;
    public static JLabel label;

    public GUI() {
        super("iTClient - _phl0w (xRS06)");
        createForm();
        try {
            UIManager.setLookAndFeel(UIManager
                    .getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(true);
        setSize(765, 503);
        setLocationRelativeTo(null);
        //Resized = 970x540
        //Normal = 780 x 590
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
    }

    private void createForm() {
        label = new JLabel("Initializing..");
        try {
            label = new JLabel("Loading Client...");
            label.setForeground(Color.GRAY.brighter());
            label.setFont(new Font("Consolas", Font.BOLD, 21));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.TOP);
            getContentPane().add(label);
            loadClasses();
            getContentPane().remove(label);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClasses() {
        changeLabelText(label, "Client being handled...");
        ClassLoader loader;
        try {
            loader = new URLClassLoader(new URL[]{new File((Constants.JAR_PATH))
                    .toURI().toURL()});
            Class<?> client = loader.loadClass("Client");
            startApplet(client);
            Variables.setClassLoader(loader);
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startApplet(final Class<?> c) {
        changeLabelText(label, "Applet started (JAR: " + Constants.JAR_PATH + ").");
        try {
            Variables.setApplet((Applet) c.newInstance());
            Variables.getApplet().setStub(this);
            Variables.getApplet().init();
            Variables.getApplet().start();
            final Applet app = Variables.getApplet();
            getContentPane().add(app, BorderLayout.CENTER);
            pack();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void changeLabelText(final JLabel l, final String text) {
        log(text);
        try {
            EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    l.setText(text);
                }
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void log(String text) {
        Logging.log(text, GUI.class);
    }

    public void appletResize(int width, int height) {
    }

    public final URL getDocumentBase() {
        try {
            return new URL(Constants.PROJECT_RS_06_URL_BASE);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public final URL getCodeBase() {
        try {
            return new URL(Constants.PROJECT_RS_06_URL_BASE);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public final AppletContext getAppletContext() {
        return null;
    }

    public final String getParameter(String name) {
        return Variables.getParameters().get(name);
    }
}
