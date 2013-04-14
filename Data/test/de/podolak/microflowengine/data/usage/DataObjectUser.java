package de.podolak.microflowengine.data.usage;

import de.podolak.microflowengine.data.DataObject;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Dirk Podolak
 */
public class DataObjectUser implements PropertyChangeListener {
    private DataObject dataObject;
    private int number;

    public DataObjectUser(DataObject dataObject, int number) {
        this.dataObject = dataObject;
        this.number = number;
        this.dataObject.addPropertyChangeListener(this);
    }

    public void changeData() {
        dataObject.setProperty("someText", "new text from data object user " + number);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(
                "data object user " + number + " has been notified by the change of the property '" +
                evt.getPropertyName() + "' that changed it's value from '" +
                evt.getOldValue() + "' to '" +
                evt.getNewValue() + "'");
    }
    
}
