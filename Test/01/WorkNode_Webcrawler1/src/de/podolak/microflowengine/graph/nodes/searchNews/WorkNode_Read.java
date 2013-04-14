package de.podolak.microflowengine.graph.nodes.searchNews;

import de.podolak.microflowengine.graph.nodes.DefaultNode;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

/**
 *
 * @author Dirk Podolak
 */
public class WorkNode_Read extends DefaultNode {

    // http://www.google.de/search?q=nanotechnologie
    
    public WorkNode_Read() {
    }

//    @Override
//    public void execution() {
//        HttpClient httpClient = (HttpClient)dataObject.getAttribute("httpClient");
//        String url = (String)dataObject.getAttribute("url");
//        
//        if (httpClient != null) {
//            HttpGet httpget = new HttpGet(url);
//
//            System.out.println("executing request " + httpget.getURI());
//
//            // Create a response handler
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            String responseBody = null;
//            
//            try {
//                responseBody = httpClient.execute(httpget, responseHandler);
//            } catch (ClientProtocolException ex) {
//                Logger.getLogger(WorkNode_Read.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(WorkNode_Read.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            System.out.println("----------------------------------------");
//            System.out.println(responseBody);
//            System.out.println("----------------------------------------");
//        }
//    }
    
    @Override
    public void execution() {
        HttpClient httpClient = (HttpClient)this.dataObject.getProperty("httpClient");
        String url = (String)dataObject.getProperty("url");
        
        if (httpClient != null) {
            HttpGet httpget = new HttpGet(url);

            System.out.println("executing request " + httpget.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = null;
            
            try {
                responseBody = httpClient.execute(httpget, responseHandler);
            } catch (ClientProtocolException ex) {
                Logger.getLogger(WorkNode_Read.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WorkNode_Read.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
        }
    }
    
}
