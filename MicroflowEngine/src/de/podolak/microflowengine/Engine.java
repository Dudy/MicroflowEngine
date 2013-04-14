package de.podolak.microflowengine;

import de.podolak.microflowengine.data.DataObject;
import de.podolak.microflowengine.graph.Edge;
import de.podolak.microflowengine.graph.Graph;
import de.podolak.microflowengine.graph.nodes.Node;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Dirk Podolak
 */
public class Engine {

    private Graph graph;
    private ExecutorService executorService;

    public Engine() {
        executorService = Executors.newCachedThreadPool();
    }
    
    public void executeGraph(String filename, DataObject dataObject) {
        graph = Graph.loadGraph(filename);
        
        if (graph != null) {
            executeNode(graph.getRootNode(), dataObject);
        }
    }
    
    private void executeNode(Node node, DataObject dataObject) {
        node.initialization(dataObject);
        
        ExecutionUnit executionUnit = new ExecutionUnit(node, this);
        executorService.execute(executionUnit);
    }
    
    void executionCallback(Node node) {
        for (Edge edge : node.getEdgeList()) {
            if (edge.getWeight() > 0) {
                edge.setWeight(edge.getWeight() - 1);
                
                //TODO: warum kopiere ich hier das Datenobjekt ???
                
                executeNode(edge.getEnd(), node.finalization().copy());
            }
        }
    }
    
}