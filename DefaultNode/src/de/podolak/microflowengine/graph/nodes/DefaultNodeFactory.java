package de.podolak.microflowengine.graph.nodes;

import de.microflowengine.utilities.StringUtilities;

/**
 * This is the <code>NodeFactory</code> for <code>DefaultNode</code>s.<br/>
 * 
 * Use <code>getInstance()</code> to get the singleton instance of this.
 * 
 * @author Dirk Podolak
 */
public class DefaultNodeFactory implements NodeFactory {
    
    /** a singleton instance of this */
    private static NodeFactory instance;

    protected DefaultNodeFactory() {
    }

    /**
     * Returns the singleton instance of this <code>DefaultNodeFactory</code>.
     * 
     * @return the singleton instance of this class
     */
    public static NodeFactory getInstance() {
        if (instance == null) {
            instance = new DefaultNodeFactory();
        }
        
        return instance;
    }
    
    /**
     * Returns a <code>DefaultNode</code> that has been constructed from the
     * data given in the <code>nodeString</code> parameter.<br/>
     * 
     * This is the main deserialization method for a <code>DefaultNode</code>.<br/>
     * The <code>nodeString</code> mußt contain the output of the <code>
     * toString()</code> method of a <code>DefaultNode</code>.
     * 
     * @param nodeString hte textual representation of a <code>DefaultNode</code>
     * @return a <code>DefaultNode</code>
     */
    @Override
    public Node getNodeFromString(String nodeString) {
        nodeString = StringUtilities.stripBlock(nodeString, Node.BLOCKNAME);
        
        String[] attributes = nodeString.split(";");
        Node node = new DefaultNode();
        
        for (String attribute : attributes) {
            String[] keyValue = attribute.split("=");
            node.setAttribute(keyValue[0], keyValue[1]);
        }
        
        return node;
    }
    
}
