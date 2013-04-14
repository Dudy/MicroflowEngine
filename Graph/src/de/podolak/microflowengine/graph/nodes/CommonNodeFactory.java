package de.podolak.microflowengine.graph.nodes;

import de.microflowengine.utilities.StringUtilities;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This common node factory produces <code>Node</code>s given the textual
 * representation.
 * 
 * @author Dirk Podolak
 * @see de.podolak.microflowengine.graph.nodes.DefaultNode
 */
public class CommonNodeFactory {
    
    /**
     * Returns an instance of <code>Node</code>.
     * The <code>nodeString</code> has to contain a class attribute.
     * More additional attributes will be added to the class.
     * Example:
     * nodestring = "
     *      node=[
     *          class=de.podolak.microflowengine.graph.nodes.DefaultNode;
     *          name=myNode
     *      ]"
     * Outcome would be a node of type <code>DefaultNode</code> with a
     * name of "myNode".
     * 
     * Returns <code>null</code> if anything goes wrong. The nodeString should
     * not be <code>null</code> or empty, a class attribute must be given, the
     * corresponding class must be in the classpath, and so on.
     * 
     * @param nodeString textual representation of a node
     * @return instance of <code>Node</code> of specified type or null
     */
    public static Node getNodeFromString(String nodeString) {
        Node node = null;
        
        if (nodeString != null && !nodeString.isEmpty()) {
            // remove enclosing "node=[...]" if necessary
            nodeString = StringUtilities.stripBlock(nodeString, Node.BLOCKNAME);

            // get node class
            String className = StringUtilities.getValue(nodeString, "class");

            // instantiate node and set attributes
            try {
                node = (Node)Class.forName(className).newInstance();
                node.setAttributes(nodeString);
            } catch (InstantiationException ex) {
                Logger.getLogger(CommonNodeFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CommonNodeFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CommonNodeFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return node;
    }
}
