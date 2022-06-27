package totalrecall;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WgetArgValid {
    private String[] args;

    public WgetArgValid(String[] args) {
        this.args = args;
        validation(args);
    }

    public void validation(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Arguments parameters: URL, minimum runtime, name output file.");
        }
        try {
            URL u = new URL(args[0]);
        } catch (MalformedURLException e) { 
            throw new IllegalArgumentException("The selected URL is not valid.");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("The specified program execution time must be positive.");
        }
        if (!isValidName(args[2])) {
            throw new IllegalArgumentException("The file name is incorrect.");
        }
    }

    public static boolean isValidName(String text) {
        Pattern pattern = Pattern.compile(
                "# Match a valid Windows filename (unspecified file system).\n"
                        + "^# Anchor to start of string.\n"
                        + "(?!# Assert filename is not: CON, PRN, \n"
                        + "(?:# AUX, NUL, COM1, COM2, COM3, COM4, \n"
                        + "CON|PRN|AUX|NUL|# COM5, COM6, COM7, COM8, COM9,\n"
                        + "COM[1-9]|LPT[1-9]# LPT1, LPT2, LPT3, LPT4, LPT5,\n"
                        + ")# LPT6, LPT7, LPT8, and LPT9...\n"
                        + "(?:\\.[^.]*)?# followed by optional extension\n"
                        + "$# and end of string\n"
                        + ")# End negative lookahead assertion. \n"
                        + "[^<>:\"/\\\\|?*\\x00-\\x1F]*# Zero or more valid filename chars.\n"
                        + "[^<>:\"/\\\\|?*\\x00-\\x1F.]# Last char is not a space or dot.\n"
                        + "$# Anchor to end of string.",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public String getUrl() {
        return args[0];
    }

    public int getTime() {
        return Integer.parseInt(args[1]);
    }

    public String getFileName() {
        return args[2];
    }
}
