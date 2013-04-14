package de.podolak.microflowengine.graph.nodes;

import de.podolak.microflowengine.data.DataObject;
import java.util.List;
import de.podolak.microflowengine.graph.Edge;

/**
 * The default interface of a <code>Node</code>.
 * 
 * @author Dirk Podolak
 */
public interface Node {
    
    // static identifier of all nodes
    public static final String BLOCKNAME = "node";

    // every node got to have an ID
    long getId();
    
    // edge handling
    List<Edge> getEdgeList();
    void addEdge(Edge edge);
    void removeEdge(Edge edge);
    
    // life cycle handling
    void initialization(DataObject dataObject);
    void execution();
    DataObject finalization();

    // data handling
    void setAttributes(String nodeString);
    void setAttribute(String attributeName, String attributeValue);
    Object getAttribute(String attributeName);
    
}
