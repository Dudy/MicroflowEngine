package de.podolak.microflowengine.graph;

import de.microflowengine.utilities.StringUtilities;
import de.podolak.microflowengine.graph.nodes.Node;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Dirk Podolak
 */
public class Edge {
    
    public static final String BLOCKNAME = "edge";

    private long id = new Random().nextLong();
    private Node start;
    private Node end;
    private int weight;

    public Edge() {
    }

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
        this.start.addEdge(Edge.this);
    }
    
    public long getId() {
        return id;
    }

    /**
     * @return the start
     */
    public Node getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Node start) {
        this.start = start;
        this.start.addEdge(Edge.this);
    }

    /**
     * @return the end
     */
    public Node getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Node end) {
        this.end = end;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public void setAttribute(String attributeName, String attributeValue) {
        if ("id".equals(attributeName)) {
            id = Long.parseLong(attributeValue);
        } else if ("weight".equals(attributeName)) {
            weight = Integer.parseInt(attributeValue);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(BLOCKNAME);
        stringBuilder.append("=[");
        
        stringBuilder.append("id=");
        stringBuilder.append(id);
        stringBuilder.append(";");
        
        stringBuilder.append("start.id=");
        stringBuilder.append(start.getId());
        stringBuilder.append(";");
        
        stringBuilder.append("end.id=");
        stringBuilder.append(end.getId());
        stringBuilder.append(";");
        
        stringBuilder.append("weight=");
        stringBuilder.append(weight);
        
        stringBuilder.append("]");
        
        return stringBuilder.toString();
    }

    public static Edge getEdgeFromString(String edgeString, HashMap<Long,Node> nodes) {
        Edge edge = new Edge();
        
        edgeString = StringUtilities.stripBlock(edgeString, Edge.BLOCKNAME);
        
        String[] attributes = edgeString.split(";");
        String[] keyValue = null;
        
        for (String attributeEntry : attributes) {
            keyValue = attributeEntry.split("=");
            
            if ("start.id".equals(keyValue[0])) {
                edge.setStart(nodes.get(Long.parseLong(keyValue[1])));
            } else if ("end.id".equals(keyValue[0])) {
                edge.setEnd(nodes.get(Long.parseLong(keyValue[1])));
            } else {
                edge.setAttribute(keyValue[0], keyValue[1]);
            }
        }
        
        return edge;
    }
}
