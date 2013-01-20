package rs.lulz.net;

import rs.lulz.utilities.Constants;
import rs.lulz.utilities.Logging;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {

    public static boolean download(final File output, final String source) {
        try {
            if (output.mkdirs()) {
                Logging.log("Made directory in " + Constants.FOLDER_PATH, Downloader.class);
            }
            if (output.exists() && !output.delete()) {
                throw new RuntimeException("Output directory exists and can not be removed.");
            }
            if (!output.createNewFile()) {
                throw new RuntimeException("Could not create new JAR file.");
            }
            final ReadableByteChannel rbc = Channels.newChannel(new URL(source).openStream());
            final FileOutputStream fos = new FileOutputStream(output);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            fos.close();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}