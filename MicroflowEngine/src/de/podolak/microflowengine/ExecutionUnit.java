package de.podolak.microflowengine;

import de.podolak.microflowengine.graph.nodes.Node;

/**
 *
 * @author Dirk Podolak
 */
public class ExecutionUnit extends Thread {
    
    private Node node;
    
    private Engine engine;

    public ExecutionUnit(Node node) {
        this.node = node;
    }

    public ExecutionUnit(Node node, Engine engine) {
        this.node = node;
        this.engine = engine;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " ::: " + node.getAttribute("name") + " ::: " + node.getId());
        node.execution();
        engine.executionCallback(node);
    }
    
}
