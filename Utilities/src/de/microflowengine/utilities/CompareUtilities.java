package de.microflowengine.utilities;

import java.util.logging.Logger;

/**
 *
 * @author Dirk Podolak
 */
public class CompareUtilities {
    
    private static final Logger LOGGER = Logger.getLogger(CompareUtilities.class.getName());

    public static int compareObjects(Comparable o1, Comparable o2) {
        int comparison = 0;
        
        if (o1 == null && o2 == null) {
            comparison = 0;
        } else if (o1 != null && o2 == null) {
            comparison = 1;
        } else if (o1 == null && o2 != null) {
            comparison = -1;
        } else {
            comparison = o1.compareTo(o2);
        }
        
        return comparison;
    }
    
    public static boolean equals(Object o1, Object o2) {
        boolean equal = false;
        
        if (o1 == null && o2 == null) {
            equal = true;
        } else if (o1 != null && o2 == null) {
            equal = false;
        } else if (o1 == null && o2 != null) {
            equal = false;
        } else {
            equal = o1.equals(o2);
        }
        
        return equal;
    }
    
}
