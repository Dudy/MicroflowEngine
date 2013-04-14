package de.podolak.microflowengine.graph.nodes;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Dirk Podolak
 */
public class WorkNode_Connect extends DefaultNode {
    
    public WorkNode_Connect() {
    }

//    @Override
//    public void execution() {
//        dataObject.setAttribute("httpClient", new DefaultHttpClient());
//    }
    
    @Override
    public void execution() {
        dataObject.setProperty("httpClient", new DefaultHttpClient());
    }
    
}
