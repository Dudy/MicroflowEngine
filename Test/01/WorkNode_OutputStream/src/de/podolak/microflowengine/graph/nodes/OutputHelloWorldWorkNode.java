package de.podolak.microflowengine.graph.nodes;

import java.io.PrintStream;

/**
 *
 * @author Dirk Podolak
 */
public class OutputHelloWorldWorkNode extends DefaultNode {

    public OutputHelloWorldWorkNode() {
    }

    @Override
    public void execution() {
//        PrintStream outputStream = (PrintStream) dataObject.getAttribute("outputStream");
//        String outputName = (String) dataObject.getAttribute("name");
//        
//        outputStream.println("Hello World, " + outputName);
        
        PrintStream outputStream = (PrintStream) dataObject.getProperty("outputStream");
        String outputName = (String) dataObject.getProperty("name");
        
        outputStream.println("Hello World, " + outputName);
    }
    
}
