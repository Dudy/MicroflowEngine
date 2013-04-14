package de.podolak.microflowengine.data;

import de.microflowengine.utilities.CompareUtilities;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This is a container for external data.<br/><br/>
 * 
 * Supports property change event handling.<br/><br/>
 * 
 * Example:<br/><br/>
 * 
 * <pre>
 * public class DataObjectUser implements PropertyChangeListener {
 *     private DataObject dataObject;
 *     private int number;
 * 
 *     public DataObjectUser(DataObject dataObject, int number) {
 *         this.dataObject = dataObject;
 *         this.number = number;
 *         this.dataObject.addPropertyChangeListener(this);
 *     }
 *
 *     public void changeData() {
 *         dataObject.setProperty("someText", "new text from data object user " + number);
 *     }
 *
 *     public void propertyChange(PropertyChangeEvent evt) {
 *         System.out.println(
 *                 "data object user " + number + " has been notified by the change of the property '" +
 *                 evt.getPropertyName() + "' that changed it's value from '" +
 *                 evt.getOldValue() + "' to '" +
 *                 evt.getNewValue() + "'");
 *     }
 * }
 * 
 * public class Main {
 *     public static void main(String[] args) {
 *         DataObject dataObject = new DataObject();
 *         DataObjectUser dataObjectUser1 = new DataObjectUser(dataObject, 1);
 *         DataObjectUser dataObjectUser2 = new DataObjectUser(dataObject, 2);
 *         
 *         dataObjectUser1.changeData();
 *         System.out.println("---------------------");
 *         dataObjectUser2.changeData();
 *     }
 * }
 * </pre>
 * 
 * @author Dirk Podolak
 */
public class DataObject {

    /** internal container mapping string keys on arbitrary objects */
    private HashMap<String, Object> properties;
    /** property change support */
    private PropertyChangeSupport propertyChangeSupport;

    public DataObject() {
        properties = new HashMap<String, Object>();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * Adds a listener to the property change support.
     * 
     * @param listener the listener to add
     */
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    /**
     * Removes a listener from the property change support.
     * 
     * @param listener the listener to remove
     */
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    /**
     * Getter for the property <code>propertyName</code>.
     * 
     * @param propertyName name of the property to get
     * @return value of property <code>propertyName</code> or null if there is no such
     */
    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }
    
    /**
     * Setter for the property <code>propertyName</code>.
     * Registered <code>PropertyChangeListener</code>s are notified if the new value
     * differs from the old value or no property of that name previously existed.
     * 
     * @param propertyName name of the property to set
     * @param value new value of the property
     */
    public void setProperty(String propertyName, Object value) {
        Object oldValue = properties.get(propertyName);
        
        if (!CompareUtilities.equals(oldValue, value)) {
            properties.put(propertyName, value);
            propertyChangeSupport.firePropertyChange(propertyName, oldValue, value);
        }
    }

    /**
     * 
     * 
     * @return 
     */
    public DataObject copy() {
        DataObject copy = new DataObject();
        
        // do deep copy
        for (Entry<String, Object> entry : properties.entrySet()) {
            
            //TODO: warum reicht es hier, wenn ich eine Kopie des Schlüssels
            // erstelle? Ich müßte doch eigentlich eine Kopie des Wertobjekts
            // erstellen, oder? Siehe auch Unittest dazu.
            
            copy.setProperty(new String(entry.getKey().toCharArray()), entry.getValue());
            //copy.setProperty(new String(entry.getKey()), entry.getValue());
            //copy.setProperty(entry.getKey(), entry.getValue());
        }
        
        return copy;
    }

    @Override
    public String toString() {
        return "DataObject with " + properties.size() + " properties";
    }
}
