package de.podolak.microflowengine.graph.nodes;

import org.apache.http.client.HttpClient;

/**
 *
 * @author Dirk Podolak
 */
public class WorkNode_Disconnect extends DefaultNode {

    public WorkNode_Disconnect() {
    }

//    @Override
//    public void execution() {
//        HttpClient httpClient = (HttpClient)dataObject.getAttribute("httpClient");
//        
//        if (httpClient != null) {
//            httpClient.getConnectionManager().shutdown();
//        }
//    }
    
    @Override
    public void execution() {
        HttpClient httpClient = (HttpClient)dataObject.getProperty("httpClient");
        
        if (httpClient != null) {
            httpClient.getConnectionManager().shutdown();
        }
    }
    
}
