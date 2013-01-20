package rs.lulz.utilities;

public interface UpdaterConfiguration {

    /**
     * Continues even if a critical error is found.
     */
    public final int SAFE_MODE = 0x1;

    /**
     * Loads the manifest files from a JAR as well as the class files (NOT RECOMMENDED).
     */
    public final int LOAD_META = 0x2;

    /**
     * Searches for a JAR in a given directory and runs off of it.
     */
    public final int FORCE_CACHE_LOAD = 0x4;

    /**
     * Adapts to any given RSPS jar, and uses recommended settings.
     */
    public final int ADAPT = 0x8;

    /**
     * Forces a load on a SoulSplit JAR
     */
    public final int FORCE_SOUL_SPLIT_LOAD = 0xF;

    /**
     * Forces a load on a NearReality JAR
     */
    public final int FORCE_NEAR_REALITY_LOAD = 0x1F;

}