package de.microflowengine.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dirk Podolak
 */
public class StringUtilities {
    
    public static final String FIELD_DELIMITER = ";";
    public static final String OBJECT_DELIMITER = "#";
    
// einfache Formatierung:
//System.out.format( "|%10.2f|%n", 123.456 ); // |    123,46|
//System.out.format( "|%-10.2f|", 123.456 );  // |123,46    |

    /**
     * Extracts a list of key-value pairs separated by semicolon with enclosing
     * brackets and the textual identifier of the content of the brackets from the
     * given container.
     * The <code>container</code> is expected to show the following format:
     * [...arbitraty data...]blockidentifier=[key1=value1;...;keyN=valueN][...arbitraty data...]
     * 
     * Example:
     * container      : "dataBlock=[someIntegerData=1;someComposedData=[id=2;foo=bar];myIdentifier=[key1=value1];boolVal=true]"
     * blockidentifier: "myIdentifier"
     * returns        : "myIdentifier=[key1=value1]"
     * 
     * @param container the container of the data
     * @param blockidentifier identifier of the data block within the container
     * @return the extracted block of data
     */
    public static String extractBlock(String container, String block) {
        block += "=[";
        
        int startIndex = container.indexOf(block);
        int endIndex = -1;
        int bracketCount = 1;
        
        for (int i = startIndex + block.length(); i < container.length(); i++) {
            if (container.charAt(i) == '[') {
                bracketCount++;
            }
            
            if (container.charAt(i) == ']') {
                bracketCount--;
            }
            
            if (bracketCount == 0) {
                endIndex = i;
                break;
            }
        }
        
        String extractedBlock = "";
        
        if (startIndex >= 0 && endIndex >= 0) {
            extractedBlock = container.substring(startIndex, endIndex + 1);
        }
        
        return extractedBlock;
    }

    /**
     * Strips off the enclosing brackets and the identifier of the <code>blockidentifier
     * </code> from the <code>container</code> if possible.
     * The <code>container</code> is expected to show the following format:
     * blockidentifier=[attribute1=value1;...;attributeN=valueN]
     * The <code>blockidentifier</code> may be omitted, so this method would only
     * strip off the brackets.
     * Example:
     * blockidentifier: "coordinates"
     * container      : "coordinates=[x=1;y=2]"
     * returns        : "x=1;y=2"
     * 
     * @param container the container of the data
     * @param blockidentifier identifier of the data block within the container
     * @return the container with removed block identifier data
     */
    public static String stripBlock(String container, String blockidentifier) {
        String strippedBlock = container;
        
        if (strippedBlock.endsWith("]")) {
            if (strippedBlock.startsWith(blockidentifier + "=[")) {
                strippedBlock = strippedBlock.substring(blockidentifier.length() + 2, strippedBlock.length() - 1);
            } else if (strippedBlock.startsWith("[")) {
                strippedBlock = strippedBlock.substring(1, strippedBlock.length() - 1);
            }
        }
        
        return strippedBlock;
    }
    
    /**
     * Removes a substring from a string.
     * Example:
     * container        : "longUglyString"
     * substringToRemove: "Ugly"
     * returns          : "longString"
     * 
     * @param container string to remove the substring from
     * @param substringToRemove substring to remove
     * @return containerstring without substring
     */
    public static String removeSubstringFromString(String container, String substringToRemove) {
        return container.replace(substringToRemove, "");
    }

    /**
     * Returns the value of a key-value pair within the data denoted by the key.
     * Example:
     * data   : "id=5;class=de.podolak.microflowengine.graph.nodes.DefaultNode;name=myNode"
     * key    : "class"
     * returns: "de.podolak.microflowengine.graph.nodes.DefaultNode"
     * 
     * @param data the string data to search within
     * @param key the key to look for
     * @return the value of the key within the data
     */
    public static String getValue(String data, String key) {
        String fieldValue = null;
        
        if (
                data != null && !data.isEmpty() &&
                key != null && !key.isEmpty()) {
            
            List<String> fieldList = getFieldList(data);
            
            for (String f : fieldList) {
                String[] keyValue = f.split("=");
                
                if (key.equals(keyValue[0])) {
                    fieldValue = keyValue[1];
                    break;
                }
            }
        }
        
        return fieldValue;
    }

    /**
     * Returns a List of key-value pairs that has been extracted from the container.
     * The data container string format should be as follows:
     * key1=value1;key2=value2;...;keyN=valueN
     * Delimiter ist semicolon.
     * Examples:
     * container: "text=a;zahl=1;koordinaten=[x=50;y=50];an=true"
     * returns  : { "text=a"; "zahl=1"; "koordinaten=[x=50;y=50]"; "an=true" }
     * 
     * @param container a string of key-value pairs separated by semicolon
     * @return 
     */
    public static List<String> getFieldList(String container) {
        // text=a;zahl=1;koordinaten=[x=50;y=50];an=true
        List<String> fieldList = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        int inBrackets = 0;
        
        for (int i = 0; i < container.length(); i++) {
            char c = container.charAt(i);
            
            if (c == FIELD_DELIMITER.charAt(0)) {
                if (inBrackets == 0) {
                    fieldList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                } else {
                    // explicitly add field delimiter here as we do not add it by default
                    // we have to add it here because we are within brackets
                    stringBuilder.append(c);
                }
            } else if (c == '[') {
                inBrackets++;
            } else if (c == ']') {
                inBrackets--;
            }
            
            // do not add field delimiter
            if (c != FIELD_DELIMITER.charAt(0)) {
                stringBuilder.append(c);
            }
        }
        
        // add last field which is not delimited by the default delimiter
        fieldList.add(stringBuilder.toString());
        
        return fieldList;
    }
    
    /**
     * Given a string containing some xml structured data and a start position this
     * method will find the next opening tag and return the index of the corresponding
     * closing tag.
     * The <code>startPosition</code> usually denotes the start of the opening tag.
     * Returns -1 if something goes wrong
     * The input xmlData has to be stripped off of whitespaces, call <code>xmlData = removeWhitespace(xmlData)</code>
     * on your string before calling this method. It will throw an IllegalArgumentException
     * if the xmlData still contains whitespaces.
     * 
     * @param xmlData
     * @param startPosition position before or at index of opening tag
     * @return index of closing tag or -1 if something goes wrong
     * @throws IllegalArgumentException thrown if the xmlData input contains illegal whitespaces
     */
    public static int findIndexOfClosingTag(String xmlData, int startPosition) {
        String text = removeWhitespace(xmlData);
        
        if (!xmlData.equals(text)) {
            throw new IllegalArgumentException("input data contains illegal whitespaces, call removeWhitespace() before");
        }
        
        try {
            int position = startPosition;
            String nextTag = getNextTag(xmlData, position); // find opening tag
            int level = 1;

            position += nextTag.length();

            while (true) {
                nextTag = getNextTag(xmlData, position);
                position = xmlData.indexOf(nextTag, position) + nextTag.length();

                if (!nextTag.endsWith("/>")) {
                    if (nextTag.startsWith("</")) {
                        level--;
                    } else {
                        level++;
                    }
                }

                if (level == 0) {
                    break;
                } else if (level < 0) {
                    throw new IllegalArgumentException("XML document not well formed");
                }
            }

            return position - nextTag.length();
        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * Returns the given string with tabs, newlines and spaces removed.
     * Spaces as text data of a node is not removed.
     * Beware: this is very limited in functionality. Use with care!
     * Example:
     * text   : "<PersonList>
     *              <Person name="Alvin">
     *              <Person name="Brevin">
     *          <PersonList>"
     * returns: "<PersonList><Person name="Alvin"/><Person name="Brevin"/></PersonList>"
     * 
     * @param text
     * @return text without whitespace
     */
    public static String removeWhitespace(String text) {
        return text
                .replaceAll("\n", "")
                .replaceAll("\t", "")
                .replaceAll(">[ ]*<", "><");
    }
    
    /**
     * Returns the next tag in the xmlData string beginning at or after position.
     * If there is no opening bracket '<' at or after position or no closing bracket '>'
     * after the opening one <code>null</code> will be returned.
     * The opening and closing brackets are part of the return value.
     * Example:
     * xmlData : "<Person><Prename>Alvin</Prename><Surname>Chipmunk</Suername></Person>"
     * position: 3
     * returns : "<Prename>"
     * 
     * @param xmlData xml structured data with tags
     * @param position start position within the xml data to start the search at
     * @return the next tag including opening and closing brackets or <code>null</code>
     *         if there is no such
     */
    public static String getNextTag(String xmlData, int position) {
        String nextTag = null;

        // xmlData must not be null or empty and has to contain an opening bracket
        if (
                xmlData != null &&
                !xmlData.isEmpty() &&
                position < xmlData.length() &&
                xmlData.substring(position).contains("<")) {
            // start at position, move forward, search opening bracket
            // the enclosing if clause ensures there is one such
            while (xmlData.charAt(position) != '<') {
                position++;
            }

            int startIndex = position;
            
            // xmlData must also contain a closing bracket after the opening one
            if (xmlData.substring(position).contains(">")) {
                // start at position, move forward, search closing bracket
                // the enclosing if clause ensures there is one such
                while (xmlData.charAt(position) != '>') {
                    position++;
                }

                // extract tag
                nextTag = xmlData.substring(startIndex, position + 1);
            }
        }
        
        return nextTag;
    }
    
    /**
     * Returns the plain text of an xml tag.
     * Just strips off leading and trailing brackets and eventually slashes.
     * If the tag contains attributes they are also stripped off.
     * Examples:
     * xmlTag : "<Tag attr=\"value\">"
     * returns: "Tag"
     * xmlTag : "</Tag>"
     * returns: "Tag"
     * xmlTag : "<Tag/>"
     * returns: "Tag"
     * 
     * @param xmlTag xml tag to strip brackets off
     * @return the plain text of the tag
     */
    public static String extractTagName(String xmlTag) {
        String tagname = xmlTag;
        
        tagname = tagname
                .replaceAll("/>", "")
                .replaceAll(">", "")
                .replaceAll("</", "")
                .replaceAll("<", "");
        
        tagname = tagname.substring(0, tagname.indexOf(" "));
        
        return tagname;
    }
    
    // for developing purposes only
    public static void main(String[] args) {
        String xmlData =
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
        
//        System.out.println(xmlData);
//        xmlData = removeWhitespace(xmlData);
//        int startIndex = 0;
//        int endIndex = findIndexOfClosingTag(xmlData, startIndex);
//        System.out.println(xmlData.substring(startIndex + "<Personen>".length(), endIndex));
//        System.out.println("endIndex: " + endIndex);
        
//        System.out.println(removeWhitespace(xmlData));
        
//        System.out.println(getNextTag(removeWhitespace(xmlData), 0));
//        System.out.println(getNextTag(removeWhitespace(xmlData), 2));
//        System.out.println(getNextTag(removeWhitespace(xmlData), 12));
       
//        System.out.println(xmlData);
//        xmlData = removeWhitespace(xmlData);
//        int startIndex = 2489;
//        int endIndex = findIndexOfClosingTag(xmlData, startIndex);
//        
//        if (endIndex > -1) {
//            System.out.println(xmlData.substring(startIndex + "<Personen>".length(), endIndex));        
//        } else {
//            System.out.println("could not find closing tag from position " + startIndex + " in data:\n" + xmlData);
//        }
    }
}
