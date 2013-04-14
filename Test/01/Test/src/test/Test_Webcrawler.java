package test;

import de.podolak.microflowengine.Engine;
import de.podolak.microflowengine.data.DataObject;

/**
 *
 * @author Dirk Podolak
 */
public class Test_Webcrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Test_Webcrawler test = new Test_Webcrawler();
        test.test01();
    }

    public void test01() {
        String filename = "./src/testdata/webcrawler01.dat";
        Engine engine = new Engine();
        DataObject dataObject = new DataObject();

        dataObject.setProperty("url", "http://www.google.de");

        engine.executeGraph(filename, dataObject);
    }
    
}
