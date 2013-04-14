package de.microflowengine.utilities;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dirk Podolak
 */
public class StringUtilitiesTest {
    
    public StringUtilitiesTest() {
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
    
    /**
     * Test of extractBlock method, of class StringUtilities.
     */
    @Test
    public void testExtractBlock() {
        System.out.println("extractBlock");
        String container = "field=[id=1;coordinates=[x=1;y=2];important=true]";
        String blockidentifier = "coordinates";
        String expResult = "coordinates=[x=1;y=2]";
        String result = StringUtilities.extractBlock(container, blockidentifier);
        assertEquals(expResult, result);
        
        container = "node=[class=de.podolak.microflowengine.graph.nodes.DefaultNode;id=5304099687645249001;name=2]";
        blockidentifier = "id";
        expResult = "id";
        result = StringUtilities.stripBlock(blockidentifier, container);
        assertEquals(expResult, result);
    }

    /**
     * Test of stripBlock method, of class StringUtilities.
     */
    @Test
    public void testStripBlock() {
        System.out.println("stripBlock");
        String blockidentifier = "coordinates";
        String container = "coordinates=[x=1;y=2]";
        String expResult = "x=1;y=2";
        String result = StringUtilities.stripBlock(container, blockidentifier);
        assertEquals(expResult, result);
        
        blockidentifier = "node";
        container = "node=[class=de.podolak.microflowengine.graph.nodes.DefaultNode;id=5304099687645249001;name=2]";
        expResult = "class=de.podolak.microflowengine.graph.nodes.DefaultNode;id=5304099687645249001;name=2";
        result = StringUtilities.stripBlock(container, blockidentifier);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeSubstringFromString method, of class StringUtilities.
     */
    @Test
    public void testRemoveSubstringFromString() {
        System.out.println("removeSubstringFromString");
        String substringToRemove = "__with an ugly substring___ ";
        String expResultStart = "I am a string ";
        String expResultEnd = "that's made for testing purposes.";
        String expResult = expResultStart + expResultEnd;
        String container = expResultStart + substringToRemove + expResultEnd;
        String result = StringUtilities.removeSubstringFromString(container, substringToRemove);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class StringUtilities.
     */
    @Test
    public void testGetValue() {
        System.out.println("getField");
        String objectString = "id=5;class=de.podolak.microflowengine.graph.nodes.DefaultNode;name=myNode";
        String field = "class";
        String expResult = "de.podolak.microflowengine.graph.nodes.DefaultNode";
        String result = StringUtilities.getValue(objectString, field);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFieldList method, of class StringUtilities.
     */
    @Test
    public void testGetFieldList() {
        System.out.println("getFieldList");
        String container = "text=a;zahl=1;koordinaten=[x=50;y=50];an=true";
        Object[] expResult = new Object[] { "text=a", "zahl=1", "koordinaten=[x=50;y=50]", "an=true" };
        Object[] result = StringUtilities.getFieldList(container).toArray();
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of findIndexOfClosingTag method, of class StringUtilities.
     */
    @Test
    public void testFindIndexOfClosingTag() {
        System.out.println("findIndexOfClosingTag");

        boolean illegalArgumentExceptionThrown = false;
        String xmlData =
                "<Person name=\"Dudy\" yearOfBirth=\"1975\">" +
                "<AgeDescription condition=\"year of birth < 1970\"><Type>old</Type></AgeDescription>" +
                "<AgeDescription condition=\"year of birth > 1985\"><Type>young</Type></AgeDescription>" +
                "</Person>";
        int startPosition = 39;
        int expResult = 104;
        int result = StringUtilities.findIndexOfClosingTag(xmlData, startPosition);
        
        System.out.println(xmlData.substring(startPosition, expResult) + "</AgeDescription>");
        System.out.println(xmlData.substring(startPosition, result) + "</AgeDescription>");
        
        assertEquals(expResult, result);
        assertFalse(illegalArgumentExceptionThrown);
        
        xmlData =
                "<Personen>\n" +
                "   <Person>\n" +
                "       <Vorname>Dirk</Vorname>\n" +
                "       <Nachname>Podolak</Nachname>\n" +
                "       <Geburtsort>Groß Gerau</Geburtsort>\n" +
                "       <Test>  Text mit Leerzeichen vorne und hinten  </Test>\n" +
                "       <Einzeltag mitAttribut=\"ja\" />\n" +
                "   </Person>\n" +
                "   <Person>\n" +
                "       <Vorname>Daniela</Vorname>\n" +
                "       <Nachname>Stößel</Nachname>\n" +
                "   </Person>\n" +
                "</Personen>\n";
        System.out.println(xmlData);
        
        try {
            StringUtilities.findIndexOfClosingTag(xmlData, startPosition);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionThrown = true;
        }
        
        xmlData = StringUtilities.removeWhitespace(xmlData);
        
        startPosition = 0;
        expResult = 267;
        result = StringUtilities.findIndexOfClosingTag(xmlData, startPosition);
        
        System.out.println(xmlData.substring(startPosition + "<Personen>".length(), expResult));
        System.out.println(xmlData.substring(startPosition + "<Personen>".length(), result));
        
        assertEquals(expResult, result);
        assertTrue(illegalArgumentExceptionThrown);
    }

    /**
     * Test of removeWhitespace method, of class StringUtilities.
     */
    @Test
    public void testRemoveWhitespace() {
        System.out.println("removeWhitespace");
        String text =
                "<Personen>\n" +
                "   <Person>\n" +
                "       <Vorname>Dirk</Vorname>\n" +
                "       <Nachname>Podolak</Nachname>\n" +
                "       <Geburtsort>Groß Gerau</Geburtsort>\n" +
                "       <Test>  Text mit Leerzeichen vorne und hinten  </Test>\n" +
                "       <Einzeltag mitAttribut=\"ja\" />\n" +
                "   </Person>\n" +
                "   <Person>\n" +
                "       <Vorname>Daniela</Vorname>\n" +
                "       <Nachname>Stößel</Nachname>\n" +
                "   </Person>\n" +
                "</Personen>\n";
        String expResult = "<Personen><Person><Vorname>Dirk</Vorname><Nachname>Podolak</Nachname><Geburtsort>Groß Gerau</Geburtsort><Test>  Text mit Leerzeichen vorne und hinten  </Test><Einzeltag mitAttribut=\"ja\" /></Person><Person><Vorname>Daniela</Vorname><Nachname>Stößel</Nachname></Person></Personen>";
        String result = StringUtilities.removeWhitespace(text);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNextTag method, of class StringUtilities.
     */
    @Test
    public void testGetNextTag() {
        System.out.println("getNextTag");
        String xmlData = "<Personen><Person><Vorname>Dirk</Vorname><Nachname>Podolak</Nachname><Geburtsort>Groß Gerau</Geburtsort><Test>  Text mit Leerzeichen vorne und hinten  </Test><Einzeltag mitAttribut=\"ja\" /></Person><Person><Vorname>Daniela</Vorname><Nachname>Stößel</Nachname></Person></Personen>";
        int position = 0;
        String expResult = "<Personen>";
        String result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
        
        position = 2;
        expResult = "<Person>";
        result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
        
        position = 12;
        expResult = "<Vorname>";
        result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
        
        position = 275;
        expResult = null;
        result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
        
        position = xmlData.length() + 100;
        expResult = null;
        result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
        
        xmlData = "";
        position = 0;
        expResult = null;
        result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
        
        xmlData = null;
        position = 0;
        expResult = null;
        result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
        
        xmlData = "<Start><End??";
        position = 2;
        expResult = null;
        result = StringUtilities.getNextTag(xmlData, position);
        assertEquals(expResult, result);
    }

    /**
     * Test of extractTagName method, of class StringUtilities.
     */
    @Test
    public void testExtractTagName() {
        System.out.println("extractTagName");
        
        String xmlTag = "<Tag>";
        String expResult = "Tag";
        String result = StringUtilities.extractTagName(xmlTag);
        assertEquals(expResult, result);
        
        xmlTag = "</Tag>";
        expResult = "Tag";
        result = StringUtilities.extractTagName(xmlTag);
        assertEquals(expResult, result);
        
        xmlTag = "<Tag/>";
        expResult = "Tag";
        result = StringUtilities.extractTagName(xmlTag);
        assertEquals(expResult, result);
    }
}
