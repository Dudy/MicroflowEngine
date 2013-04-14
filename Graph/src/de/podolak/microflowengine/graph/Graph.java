package de.podolak.microflowengine.graph;

import de.microflowengine.utilities.AssertionUtilities;
import de.microflowengine.utilities.StringUtilities;
import de.podolak.microflowengine.graph.nodes.Node;
import de.podolak.microflowengine.graph.nodes.Nodes;
import de.podolak.microflowengine.graph.nodes.CommonNodeFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dirk Podolak
 */
public class Graph {

    // --------------------------------------------------------------------------------
    // ----- debugging only
    
    private static final boolean PRINT_AFTER_LOAD = false;
    private static final int     INDENT_DEPTH     = 4;
    
    // turns on strict parameter checking, use only in debugging
    private static final boolean BE_STRICT        = true;
    // --------------------------------------------------------------------------------
    
    public static final String BLOCKNAME = "graph";
    
    /** temporary node container for serializing cyclic graphs */
    private static Set<Node> serializedNodes;

    private Node rootNode;

    public Graph() {
    }

    public Graph(Node rootNode) {
        this();
        this.rootNode = rootNode;
    }
    
    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    // <editor-fold defaultstate="collapsed" desc=" serialization ">
    /**
     * Serializes a node.
     * This traverses down the subtree of that node, i.e. all the edges
     * of this node are walked down and their end nodes are also serialized.
     * 
     * Clear the <code>serializedNotes</code> set before serializing the
     * root node!
     * 
     * @param node node to serialize
     * @return textual representation of the node
     */
    public static String serializeNode(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        
        if (BE_STRICT) {
            AssertionUtilities.assertNotNull(node, "node");
        }
        
        // "serializedNodes" holds all nodes that are already serialized
        // so that nodes on circular paths in the graph are only serialized
        // once. Note that this set has to be cleared before serializing it
        // with this method.
        if (node != null && !serializedNodes.contains(node)) {
            serializedNodes.add(node);
            stringBuilder.append(node);
            stringBuilder.append(";");
            
            for (Edge edge : node.getEdgeList()) {
                stringBuilder.append(serializeNode(edge.getEnd()));
            }
        }
        
        return stringBuilder.toString();
    }
    
    /**
     * Serialize edges of a node.
     * This traverses down the subtree of that node, i.e. all the edges
     * of this node are serialized, walked down and their end nodes (or more
     * specifically the edges of the end nodes) are also serialized.
     * 
     * Clear the <code>serializedNotes</code> set before serializing the
     * root node!
     * 
     * @param node the node whose edges are to be serialized
     * @return textual representation of the edges of the node
     */
    public static String serializeEdges(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        
        if (BE_STRICT) {
            AssertionUtilities.assertNotNull(node, "node");
        }
        
        // "serializedNodes" holds all nodes that are already serialized
        // so that nodes on circular paths in the graph are only serialized
        // once. Note that this set has to be cleared before serializing it
        // with this method.
        if (node != null && !serializedNodes.contains(node)) {
            serializedNodes.add(node);
            
            for (Edge edge : node.getEdgeList()) {
                stringBuilder.append(serializeEdge(edge));
            }
        }
        
        return stringBuilder.toString();
    }
    
    /**
     * Serialize an edge.
     * This traverses down the subtree of the end node of this edge, i.e. all
     * the edges of the end node are serialized, walked down and their end nodes
     * (or more specifically the edges of the end nodes) are also serialized.
     * 
     * @param edge edge to serialize
     * @return textual representation of the edge
     */
    public static String serializeEdge(Edge edge) {
        StringBuilder stringBuilder = new StringBuilder();
        
        if (BE_STRICT) {
            AssertionUtilities.assertNotNull(edge, "edge");
        }
        
        stringBuilder.append(edge);
        stringBuilder.append(";");
        
        stringBuilder.append(serializeEdges(edge.getEnd()));
        
        return stringBuilder.toString();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" deserialization ">
    /**
     * Deserializes a string to a graph.
     * Returns the <code>Graph</code> object that is generated from the
     * textual representation given in the parameter.
     * 
     * @param graphString
     * @return 
     */
    public static Graph deserializeGraph(String graphString) {
        if (graphString == null) {
            throw new AssertionError("graphString is null");
        }
        
        if (graphString.isEmpty()) {
            throw new AssertionError("graphString is empty");
        }
        
        if (!graphString.contains("graph=[")) {
            throw new AssertionError("graphString does not contain graph");
        }
        
        if (!graphString.endsWith("]")) {
            throw new AssertionError("graphString does not end correctly");
        }
        
        String gs = StringUtilities.stripBlock(graphString, Graph.BLOCKNAME);
        Graph graph = new Graph();
        
        HashMap<Long,Node> nodes = deserializeNodes(gs);
        deserializeEdges(gs, nodes);
        
        graph.setRootNode(nodes.get(Long.MIN_VALUE));
        
        return graph;
    }
    
    public static HashMap<Long,Node> deserializeNodes(String ns) {
        if (ns == null) {
            throw new AssertionError("nodesString is null");
        }
        
        if (ns.isEmpty()) {
            throw new AssertionError("nodesString is empty");
        }
        
        if (!ns.contains(Nodes.BLOCKNAME + "=[")) {
            throw new AssertionError("nodesString does not contain nodes");
        }
        
        if (!ns.endsWith("]")) {
            throw new AssertionError("nodesString does not end correctly");
        }
        
        String nodesString = ns;
        HashMap<Long,Node> nodes = new HashMap<Long, Node>();
        
        nodesString = StringUtilities.extractBlock(nodesString, Nodes.BLOCKNAME);
        nodesString = StringUtilities.stripBlock(nodesString, Nodes.BLOCKNAME);
        
        boolean rootNode = true;
        
        List<String> nodesList = StringUtilities.getFieldList(nodesString);
        
        for (String nodeString : nodesList) {
            Node node = CommonNodeFactory.getNodeFromString(nodeString);
            nodes.put(node.getId(), node);
            
            if (rootNode) {
                rootNode = false;
                nodes.put(Long.MIN_VALUE, node);
            }
        }
        
        return nodes;
    }
    
    public static void deserializeEdges(String es, HashMap<Long,Node> nodes) {
        if (es == null) {
            throw new AssertionError("edgesString is null");
        }
        
        if (es.isEmpty()) {
            throw new AssertionError("edgesString is empty");
        }
        
        if (!es.contains(Edges.BLOCKNAME + "=[")) {
            throw new AssertionError("edgesString does not contain edges: " + es);
        }
        
        if (!es.endsWith("]")) {
            throw new AssertionError("edgesString does not end correctly");
        }
        
        String edgesString = es;
        edgesString = StringUtilities.extractBlock(edgesString, Edges.BLOCKNAME);
        edgesString = StringUtilities.stripBlock(edgesString, Edges.BLOCKNAME);
        
        List<String> edgesList = StringUtilities.getFieldList(edgesString);
        
        for (String edgeString : edgesList) {
            Edge.getEdgeFromString(edgeString, nodes);
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" persistence ">
    public void save(String filename) {
        saveGraph(this, new File(filename));
    }
    
    public void save(File file) {
        saveGraph(this, file);
    }
    
    public static void saveGraph(Graph graph, String filename) {
        saveGraph(graph, new File(filename));
    }
    
    public static void saveGraph(Graph graph, File file) {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(getGraphString(graph.getRootNode()));
        } catch (IOException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void load(String filename) {
        load(new File(filename));
    }
    
    public void load(File file) {
        rootNode = loadGraph(file).getRootNode();
    }
    
    public static Graph loadGraph(String filename) {
        return loadGraph(new File(filename));
    }
    
    public static Graph loadGraph(File file) {
        BufferedReader reader = null;
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        Graph graph = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(
                        line
                        .trim()
                        .replace("\n", "")
                        .replace("\t", "")
                        );
            }
            
            graph = deserializeGraph(stringBuilder.toString());
            
            if (PRINT_AFTER_LOAD) {
                System.out.println("-------------------------------------------------");
                prettyPrint(graph);
                System.out.println("-------------------------------------------------");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return graph;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" helper ">
    public static String getGraphString(Node rootNode) {
        String s;
        StringBuilder stringBuilder = new StringBuilder(Graph.BLOCKNAME + "=[");
        
        serializedNodes = new HashSet<Node>();
        stringBuilder.append(Nodes.BLOCKNAME + "=[");
        s = serializeNode(rootNode);
        if (!s.isEmpty()) {
            stringBuilder.append(s.substring(0, s.length() - 1)); // remove last semicolon
        }
        stringBuilder.append("];");
        
        serializedNodes = new HashSet<Node>();
        stringBuilder.append(Edges.BLOCKNAME + "=[");
        s = serializeEdges(rootNode);
        if (!s.isEmpty()) {
            stringBuilder.append(s.substring(0, s.length() - 1)); // remove last semicolon
        }
        stringBuilder.append("]");
        
        stringBuilder.append("]");
        
        return stringBuilder.toString();
    }
    
    public static void prettyPrint(Graph g) {
        prettyPrint(getGraphString(g.getRootNode()));
    }
    
    public static void prettyPrint(String s) {
        String emptyString = "                                           ";
        int indent = 0;
        
        for (char c : s.toCharArray()) {
            if (c == ']') {
                indent -= INDENT_DEPTH;
                System.out.println();
                System.out.print(emptyString.substring(0, indent));
            }
            
            System.out.print(c);
            
            if (c == '[') {
                indent += INDENT_DEPTH;
                System.out.println();
                System.out.print(emptyString.substring(0, indent));
            }
            
            if (c == ';') {
                System.out.println();
                System.out.print(emptyString.substring(0, indent));
            }
        }
        
        System.out.println();
    }
    // </editor-fold>
    
    @Override
    public String toString() {
        return getGraphString(rootNode);
    }
}