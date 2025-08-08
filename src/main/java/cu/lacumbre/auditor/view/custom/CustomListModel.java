package cu.lacumbre.auditor.view.custom;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class CustomListModel<T> implements ListModel<T>{

    public final ArrayList<T> list;

    public CustomListModel(ArrayList<T> arrayList) {
        if (arrayList != null) {
            this.list = arrayList;
        }else{
            this.list = new ArrayList<>();
        }
    }

    public ArrayList<T> getList() {
        return list;
    }
    
    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public T getElementAt(int index) {
        return list.get(index);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }

    @Override
    public void addListDataListener(ListDataListener l) {
        new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                
            }
        };
    }



}
