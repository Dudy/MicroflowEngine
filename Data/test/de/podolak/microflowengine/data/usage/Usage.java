package de.podolak.microflowengine.data.usage;

import de.podolak.microflowengine.data.DataObject;

/**
 *
 * @author Dirk Podolak
 */
public class Usage {

    public Usage() {
    }
    
    public static void execute() {
        DataObject dataObject = new DataObject();
        DataObjectUser dataObjectUser1 = new DataObjectUser(dataObject, 1);
        DataObjectUser dataObjectUser2 = new DataObjectUser(dataObject, 2);
        
        dataObjectUser1.changeData();
        System.out.println("---------------------");
        dataObjectUser2.changeData();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Usage.execute();
    }
    
}
