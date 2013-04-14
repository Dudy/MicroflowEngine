package test;

import de.podolak.microflowengine.Engine;
import de.podolak.microflowengine.data.DataObject;

/**
 *
 * @author Dirk Podolak
 */
public class Test_OutputStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Test_OutputStream test = new Test_OutputStream();
        test.test01();
    }

    public void test01() {
        String filename = "./src/testdata/graph_08.dat";
        Engine engine = new Engine();
        DataObject dataObject = new DataObject();

        dataObject.setProperty("name", "Dudy");

        engine.executeGraph(filename, dataObject);
    }
    
}
