/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.microflowengine.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dirk Podolak
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test01();
//        test02();
    }

    private static void test01() {
        String alles = "Gebiet=[Objekt=[ID=5000;Name=Baum;Koordinaten=[x=101;y=100];Anzahl=1];Objekt=[ID=5001;Name=Baum;Koordinaten=[x=100;y=101];Anzahl=1];Objekt=[ID=5002;Name=Baum;Koordinaten=[x=101;y=101];Anzahl=1]]";

        System.out.println(alles);
        
        Gebiet gebiet = new Gebiet();
        gebiet.readExternal(alles);
        
        System.out.println(gebiet);
    }
    
    private static void test02() {
        String object = "text=a;zahl=1;koordinaten=[x=50;y=50];an=true";
        List<String> fieldList = StringUtilities.getFieldList(object);
        
        for (String field : fieldList) {
            System.out.println(field);
        }
    }

    private static class Gebiet {

        private ArrayList<Objekt> objektList;

        public Gebiet() {
            objektList = new ArrayList<Objekt>();
        }

        private String writeExternal() {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Gebiet=[");
            
            for (Objekt objekt : objektList) {
                stringBuilder.append(objekt.writeExternal());
                stringBuilder.append(";");
            }
            
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            
            stringBuilder.append("]");
            
            return stringBuilder.toString();
        }
        
        private void readExternal(String data) {
            String block = StringUtilities.stripBlock(data, "Gebiet");
            List<String> fieldList = StringUtilities.getFieldList(block);
            
            for (String field : fieldList) {
                String[] keyValue = field.split("=", 2);
                
                if ("Objekt".equals(keyValue[0])) {
                    Objekt objekt = new Objekt();
                    objekt.readExternal(field);
                    objektList.add(objekt);
                }
            }
        }
        
        @Override
        public String toString() {
            return writeExternal();
        }
    }

    private static class Objekt {
        // Objekt=[ID=5000;Name=Baum;Koordinaten=[x=101;y=100];Anzahl=1]
        
        private long id;
        private String name;
        private Koordinaten koordinaten;
        private int anzahl;
        
        private String writeExternal() {
            return new StringBuilder()
                    .append("Objekt=[")
                        .append("ID=").append(id).append(";")
                        .append("Name=").append(name).append(";")
                        .append(koordinaten.writeExternal()).append(";")
                        .append("Anzahl=").append(anzahl)
                    .append("]")
                    .toString();
        }
        
        private void readExternal(String data) {
            String block = StringUtilities.stripBlock(data, "Objekt");
            List<String> fieldList = StringUtilities.getFieldList(block);
            
            for (String field : fieldList) {
                String[] keyValue = field.split("=", 2);
                
                if ("ID".equals(keyValue[0])) {
                    id = Long.parseLong(keyValue[1]);
                } else if ("Name".equals(keyValue[0])) {
                    name = keyValue[1];
                } else if ("Koordinaten".equals(keyValue[0])) {
                    koordinaten = new Koordinaten();
                    //koordinaten.readExternal(keyValue[1]);
                    koordinaten.readExternal(field);
                } else if ("Anzahl".equals(keyValue[0])) {
                    anzahl = Integer.parseInt(keyValue[1]);
                }
            }
        }
        
        @Override
        public String toString() {
            return writeExternal();
        }
    }
    
    private static class Koordinaten {
        // Koordinaten=[x=101;y=100]
        
        private int x;
        private int y;
        
        private String writeExternal() {
            return new StringBuilder()
                    .append("Koordinaten=[")
                        .append("x=").append(x).append(";")
                        .append("y=").append(y)
                    .append("]")
                    .toString();
        }
        
        private void readExternal(String data) {
            String block = StringUtilities.stripBlock(data, "Koordinaten");
            List<String> fieldList = StringUtilities.getFieldList(block);
            
            for (String field : fieldList) {
                String[] keyValue = field.split("=", 2);
                
                if ("x".equals(keyValue[0])) {
                    x = Integer.parseInt(keyValue[1]);
                } else if ("y".equals(keyValue[0])) {
                    y = Integer.parseInt(keyValue[1]);
                }
            }
        }
        
        @Override
        public String toString() {
            return writeExternal();
        }
    }
}
