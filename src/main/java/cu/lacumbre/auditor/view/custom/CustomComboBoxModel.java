package cu.lacumbre.auditor.view.custom;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class CustomComboBoxModel<T> implements ComboBoxModel<T> {

    private final ArrayList<T> arrayList;
    Object selectedObject;

    public CustomComboBoxModel(ArrayList<T> arrayList) {
        if (arrayList != null) {
            this.arrayList = arrayList;
        } else {
            this.arrayList = new ArrayList<>();
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if ((selectedObject != null && !selectedObject.equals(anItem))
                || selectedObject == null && anItem != null) {
            selectedObject = anItem;
        }
    }

    public ArrayList<T> getArrayList() {
        return arrayList;
    }
    
    

    @Override
    public Object getSelectedItem() {
        return selectedObject;

    }

    @Override
    public int getSize() {
        return arrayList.size();
    }

    @Override
    public T getElementAt(int index) {
        return arrayList.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }

}
