package rs.lulz.utilities;

import java.util.logging.Logger;

public class Logging {

    private static final Logger log = Logger.getLogger(Logging.class.getName());

    public static void log(final String msg, final Class<?> clazz) {
        log.info("[".concat(clazz.getName()).concat("]: ").concat(msg));
    }
}
