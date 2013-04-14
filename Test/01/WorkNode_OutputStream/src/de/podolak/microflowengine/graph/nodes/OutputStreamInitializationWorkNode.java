package de.podolak.microflowengine.graph.nodes;

import java.io.PrintStream;

/**
 *
 * @author Dirk Podolak
 */
public class OutputStreamInitializationWorkNode extends DefaultNode {

    public OutputStreamInitializationWorkNode() {
    }

    @Override
    public void execution() {
//        PrintStream outputStream = System.out;
//        dataObject.setAttribute("outputStream", outputStream);
        
        PrintStream outputStream = System.out;
        dataObject.setProperty("outputStream", outputStream);
    }
    
}
