package net.rom.lib;

public class UtilsInfo {
    public static final String GITHUB = "https://github.com/ROMVoid95/utils";
    
    public static final String VERSION_BUILD = "2";
    
    public static final String VERSION_MAJOR = "1";
    
    public static final String VERSION_MINOR = "0";
    
    public static final String VERSION_REVISION = "0";
    
    public static final String VERSION = "1".startsWith("@") ? "dev" : String.format("%s.%s.%s_%s", new Object[] { "1", "0", "0", "2" });
}
