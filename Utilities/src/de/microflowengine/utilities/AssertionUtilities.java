package de.microflowengine.utilities;

/**
 *
 * @author Dirk Podolak
 */
public class AssertionUtilities {

    public static void assertNotNull(Object o, String objectName) {
        if (o == null) {
            throw new AssertionError(objectName + " is null");
        }
    }
    
    public static void assertStringNotEmpty(String s, String stringName) {
        if (s.isEmpty()) {
            throw new AssertionError(stringName + " is empty");
        }
    }
    
    public static void assertStringContains(String s, String content, String stringName) {
        if (!s.contains(content)) {
            throw new AssertionError(stringName + " does not contain '" + content + "'");
        }
    }
    
    public static void assertStringEndsWith(String s, String content, String stringName) {
        if (!s.endsWith(content)) {
            throw new AssertionError(stringName + " does not end with '" + content + "'");
        }
    }
    
}
