package de.podolak.microflowengine.data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dirk Podolak
 */
public class DataObjectTest {
    
    public DataObjectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

//    /**
//     * Test of getAttribute method, of class DataObject.
//     */
//    @Test
//    public void testGetAttribute() {
//        System.out.println("getAttribute");
//        String attributeName = "";
//        DataObject instance = new DataObject();
//        Object expResult = null;
//        Object result = instance.getAttribute(attributeName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setAttribute method, of class DataObject.
//     */
//    @Test
//    public void testSetAttribute() {
//        System.out.println("setAttribute");
//        String attributeName = "";
//        Object value = null;
//        DataObject instance = new DataObject();
//        instance.setAttribute(attributeName, value);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of clone method, of class DataObject.
     */
    @Test
    public void testCopy() {
        System.out.println("copy");

        // create an instance
        DataObject instance = new DataObject();
        
        instance.setProperty("id", "123");
        instance.setProperty("name", "one two three");
        
        // create a copy of the instance
        DataObject copy = instance.copy();
        
        // the values of all properties must be equal
        assertEquals(instance.getProperty("id"), copy.getProperty("id"));
        assertEquals(instance.getProperty("name"), copy.getProperty("name"));

        //TODO: ich weiß noch nicht wirklich, warum die nächsten beiden Zeilen
        // funktionieren. Die Wertobjekte sind eigentlich dieselben, nur der
        // Schlüssel wurde neu erzeugt.
        
        // the values of all properties must not be the same objects
        assertNotSame(instance.getProperty("id"), copy.getProperty("id"));
        assertNotSame(instance.getProperty("name"), copy.getProperty("name"));
        
        // equality test for property "id" before and after a change
        assertTrue(instance.getProperty("id").equals(copy.getProperty("id")));
        instance.setProperty("id", "456");
        assertFalse(instance.getProperty("id").equals(copy.getProperty("id")));
    }
}
