package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.auditor.crud.Mapper;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.view.inventory.MapperGestion;
import cu.lacumbre.utils.Logger;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckerMap extends Checker{

    private final Mapper mapper;
    private final MapperGestion mapFromCuadre;

    public CheckerMap(MapperGestion invoker, Mapper mapper, String category) {
        super(invoker, mapper.getItemsCRUD(), category, false);
        this.mapFromCuadre = invoker instanceof MapperGestion ? invoker : null;
        this.mapper = mapper;
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        try {
            JCheckBox check = (JCheckBox) evt.getItemSelectable();
            JPanel parent1 = (JPanel) check.getParent();
            Component[] childs = parent1.getComponents();
            JLabel idField = (JLabel) childs[1];
            int id = Integer.parseInt(idField.getText());
            if (mapFromCuadre != null) {
                switch (evt.getStateChange()) {
                    case ItemEvent.SELECTED ->
                        mapper.save(mapFromCuadre.getSelecteditem(), (Product) mapper.getItemsCRUD().get(id), true);
                    case ItemEvent.DESELECTED ->
                        mapper.remove(mapFromCuadre.getSelecteditem(), (Product) mapper.getItemsCRUD().get(id));
                }
                mapFromCuadre.refreshFrameView();
            }
        } catch (SQLException ex) {
            Logger.getInstance().updateErrorLog(ex);
        }
    }
    
}
