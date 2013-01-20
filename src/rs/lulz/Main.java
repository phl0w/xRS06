package rs.lulz;

import rs.lulz.bot.loader.Updater;
import rs.lulz.hooks.Client;
import rs.lulz.net.Downloader;
import rs.lulz.net.RS06Crawler;
import rs.lulz.utilities.Constants;
import rs.lulz.utilities.Logging;
import rs.lulz.utilities.UpdaterConfiguration;
import rs.lulz.utilities.Variables;

import java.io.File;
import java.io.IOException;

public class Main {

    private static long lastRepaint;

    public static void main(final String... args) throws IOException {
        Logging.log("Starting the bot.", Main.class);
        final RS06Crawler crawler = new RS06Crawler();
        final String clazzName = crawler.getMainClassName();
        final String jarUrl = crawler.getJarURL();
        Variables.mainClass = clazzName.replaceAll(".class", "");
        if (Downloader.download(new File(Constants.JAR_PATH), jarUrl)) {
            Logging.log("Successfully downloaded client to: " + Constants.JAR_PATH, Main.class);
        }
        Updater u = new Updater(Constants.JAR_PATH, UpdaterConfiguration.ADAPT);
        GUI g = new GUI();
        try {
            Client c = (Client) Variables.getClassLoader().loadClass("Client").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
