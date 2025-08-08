package cu.lacumbre.auditor.view.custom;

import cu.lacumbre.auditor.model.RawMaterial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public final class CustomTM_RMDatePreviewedOutcomesSummary extends CustomAbstractTableModel {

    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList(new String[]{"ID", "Producto U/M", "Cantidad Saliente", "Cantidad Existente", "Cantidad Restante"}));

    public CustomTM_RMDatePreviewedOutcomesSummary(TreeMap<RawMaterial, Double> previewedOutcomes, TreeMap<RawMaterial, Double> existences) {
        super(previewedOutcomes.size(), COLUMN_NAMES);
        for (Map.Entry<RawMaterial, Double> entry : previewedOutcomes.entrySet()) {
            RawMaterial rawMaterial = (RawMaterial) entry.getKey();
            double neededAmmount = entry.getValue();
            double currentAmmount = existences.getOrDefault(rawMaterial, 0.0d);
            double reminningAmmount = currentAmmount - neededAmmount;
            ArrayList<Object> sublist = new ArrayList<>();
            sublist.add(rawMaterial);
            sublist.add(rawMaterial.toString());
            sublist.add(neededAmmount);
            sublist.add(currentAmmount);
            sublist.add(reminningAmmount);
            this.dataArray.add(sublist);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex > 1) {
            return decimalFormat.format(dataArray.get(rowIndex).get(columnIndex));
        } else {
            RawMaterial item = (RawMaterial) dataArray.get(rowIndex).get(0);
            if (columnIndex == 0) {
                return item.getId();
            } else {
                return dataArray.get(rowIndex).get(1) + " [" + item.getMeasureUnit().getAbrev() + "]";
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 ->
                Integer.class;
            default ->
                String.class;
        };
    }

}
