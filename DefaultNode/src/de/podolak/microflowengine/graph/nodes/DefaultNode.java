package de.podolak.microflowengine.graph.nodes;

import de.microflowengine.utilities.StringUtilities;
import de.podolak.microflowengine.data.DataObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import de.podolak.microflowengine.graph.Edge;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The default implementation of a <code>Node</code>.<br/>
 * 
 * This <code>Node</code> simply consists of a container holding some data,
 * a list of <code>Edge</code>s that leave that <code>Node</code> in the
 * <code>Graph</code> as well as a name and an ID.<br/>
 * The execution method does nothing.<br/>
 * There are also some additional attributes that you may use within this
 * <code>Node</code>. These are mainly for infrastructural and internal
 * purposes. If there is outworld data to cope with and to pass to further
 * <code>Node</code>s, use the dataObject.<br/>
 * This <code>Node</code> also implements a basic form of serialization.<br/>
 * 
 * TODO: rework de-/serialization
 * 
 * @author Dirk Podolak
 * @see de.podolak.microflowengine.data.DataObject
 * @see de.podolak.microflowengine.graph.Graph
 * @see de.podolak.microflowengine.graph.Edge
 */
public class DefaultNode implements Node {

    /** ID of that <code>Node</code> */
    protected long id;
    /** data object to hold external data */
    protected DataObject dataObject;
    /** list of edges leaving this <code>Node</code> in the <code>Graph</code> */
    protected List<Edge> edgeList;
    /** name of the <code>Node</code> */
    protected String name;
    
    /** some internal additional attributes of this <code>Node</code> */
    protected HashMap<String,String> additionalAttributes;

    public DefaultNode() {
        id = new Random().nextLong();
        dataObject = new DataObject();
        additionalAttributes = new HashMap<String, String>();
    }
    
    public DefaultNode(String name) {
        this();
        this.name = name;
    }

    /**
     * Takes the external data object into this <code>Node</code>.
     * 
     * @param dataObject the external data object to work with
     */
    @Override
    public void initialization(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    /**
     * The execution of this <code>Node</code> is a no-operation.<br/>
     * It does not have any effects on the data object.
     */
    @Override
    public void execution() {
        // noop
    }

    /**
     * Returns the external data object after all work is done.
     * 
     * @return the external data object
     */
    @Override
    public DataObject finalization() {
        return dataObject;
    }

    /**
     * Returns the list of <code>Edge</code>s that leave this <code>Node</code>
     * in the <code>Graph</code>. Returns an empty list if there are no <code>
     * Edge</code>s but will not return <code>null</code>.
     * 
     * @return list of <code>Edge</code>s
     */
    @Override
    public List<Edge> getEdgeList() {
        if (edgeList == null) {
            edgeList = new ArrayList<Edge>();
        }
        
        return edgeList;
    }
    
    /**
     * Adds an <code>Edge</code> to the list of <code>Edge</code>s leaving this
     * <code>Node</code> in the <code>Graph</code>.
     * 
     * @param edge the <code>Edge</code> to add to this <code>Node</code>
     */
    @Override
    public void addEdge(Edge edge) {
        getEdgeList().add(edge);
    }
    
    /**
     * Removes an <code>Edge</code> from the list of <code>Edge</code>s leaving
     * this <code>Node</code> in the <code>Graph</code>.
     * 
     * @param edge the <code>Edge</code> to remove from this <code>Node</code>
     */
    @Override
    public void removeEdge(Edge edge) {
        getEdgeList().remove(edge);
    }
    
    /**
     * Returns the ID of this <code>Node</code>.
     * 
     * @return the ID of this <code>Node</code>
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Returns the name of this <code>Node</code>.<br/>
     * May return <code>null</code> if no name has been set.
     * 
     * @return the name of this <code>Node</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this <code>Node</code>.
     * 
     * @param name the name of this <code>Node</code>
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets attributes of this <code>Node</code>.<br/>
     * 
     * This is mainly used for serialization issues. It takes the output of
     * the <code>toString()</code> method and reconstructs the inner state of
     * this <code>Node</code> to be the same as the <code>Node</code> that
     * printed out it's state with <code>toString()</code>. See that method
     * for further information.
     * 
     * @param nodeString textual representation of a <code>Node</code>
     * @see de.podolak.microflowengine.graph.nodes.DefaultNode#toString()
     */
    @Override
    public void setAttributes(String nodeString) {
        nodeString = StringUtilities.stripBlock(nodeString, Node.BLOCKNAME);
        
        for (String attribute : StringUtilities.getFieldList(nodeString)) {
            String[] keyValue = attribute.split("=");
            setAttribute(keyValue[0], keyValue[1]);
        }
    }
    
    /**
     * Sets the value of an attribute within this <code>Node</code>.
     * 
     * @param attributeName the name of the attribute to assign a new value to
     * @param attributeValue the new value of the attribute
     */
    @Override
    public void setAttribute(String attributeName, String attributeValue) {
        if ("id".equals(attributeName)) {
            id = Long.parseLong(attributeValue);
        } else if ("name".equals(attributeName)) {
            name = attributeValue;
        } else {
            additionalAttributes.put(attributeName, attributeValue);
        }
    }
    
    /**
     * Returns the value of an attribute of this <code>Node</code>.
     * 
     * @param attributeName name of the attribute
     * @return value of the attribute or null if there is no such
     */
    @Override
    public Object getAttribute(String attributeName) {
        Object attributeValue = null;
        
        if ("id".equals(attributeName)) {
            attributeValue = id;
        } else if ("name".equals(attributeName)) {
            attributeValue = name;
        } else {
            attributeValue = additionalAttributes.get(attributeName);
        }
        
        return attributeValue;
    }

    /**
     * Returns a <code>String</code> representation of this <code>Node</code>.<br/>
     * 
     * This is the main serialization method. It packages this <code>Node</code>
     * into a key-value pair string of the following format:<br/>
     * <pre>
     * {@code
     * <node> = "node=[name=<name>;<attributeList>id=<id>]"
     * <attributeList> = "<attribute>|<attribute><attributeList>"
     * <attribute> = "<key>=<value>;"
     * <key> => name of the attribute
     * <value> => value of the attribute
     * <name> => name of the <code>Node</code>
     * <id> => ID of the <code>Node</code>
     * }
     * </pre>
     * Example:<br/>
     * node=[name=startNode;startTime=123456789;endTime=987654321;id=10000000]
     * 
     * @return the textual representation of this <code>Node</code>
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(BLOCKNAME);
        stringBuilder.append("=[");
        
        stringBuilder.append("name=");
        stringBuilder.append(name);
        stringBuilder.append(";");
        
        stringBuilder.append(getAdditionalAttributes());
        
        stringBuilder.append("id=");
        stringBuilder.append(id);
        
        stringBuilder.append("]");
        
        return stringBuilder.toString();
    }
    
    /**
     * Returns a string representation of the additional attributes.<br/>
     * See the <code>toString()</code> method for format details.
     * 
     * @return textual representation of all additional attributes
     */
    protected String getAdditionalAttributes() {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (Entry<String, String> entry : additionalAttributes.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(";");
        }
        
        return stringBuilder.toString();
    }
    
}
