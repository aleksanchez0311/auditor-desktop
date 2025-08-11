package cu.lacumbre.auditor.view.custom;

import java.util.ArrayList;

public abstract class CustomAbstractTableModelTotalizable extends CustomAbstractTableModel {

    protected final int totalColumnIndexKey;
    protected final int[] totalColumnIndexValues;
    protected double[] totals;

    protected CustomAbstractTableModelTotalizable(int rowCount, int totalColumnIndexKey, int[] totalColumnIndexValues) {
        super(rowCount);
        this.totalColumnIndexKey = totalColumnIndexKey;
        this.totalColumnIndexValues = totalColumnIndexValues;
    }

    protected CustomAbstractTableModelTotalizable(int rowCount, ArrayList<String> columnNames, int totalColumnIndexKey, int[] totalColumnIndexValues) {
        super(rowCount, columnNames);
        this.totalColumnIndexKey = totalColumnIndexKey;
        this.totalColumnIndexValues = totalColumnIndexValues;
    }
    
    protected CustomAbstractTableModelTotalizable(int rowCount, int columnCount, ArrayList<String> columnNames, int totalColumnIndexKey, int[] totalColumnIndexValues) {
        super(rowCount, columnCount, columnNames);
        this.totalColumnIndexKey = totalColumnIndexKey;
        this.totalColumnIndexValues = totalColumnIndexValues;
    }

    @Override
    public int getRowCount() {
        return rowCount + 1;
    }

    public double[] getTotals() {
        return totals;
    }

    public void setTotals() {
        int length = columnNames.size() == columnCount ? columnCount : columnNames.size();
        totals = new double[length];
        for (int i = 0; i < totalColumnIndexValues.length; i++) {
            int totalColumnIndexValue = totalColumnIndexValues[i];
            double total = 0.0d;
            for (int row = 0; row < rowCount; row++) {
                ArrayList<Object> values = dataArray.get(row);
                total += (double) values.get(totalColumnIndexValue);
            }
            totals[totalColumnIndexValue] = total;
        }
        ArrayList<Object> subList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            subList.add(null);
        }
        subList.set(totalColumnIndexKey, "Total ($) => ");
        for (int i = 0; i < totalColumnIndexValues.length; i++) {
            int totalColumnIndexValue = totalColumnIndexValues[i];
            subList.set(totalColumnIndexValue, totals[totalColumnIndexValue]);
        }
        this.dataArray.add(subList);
    }

    public void updateTotals() {
        for (int i = 0; i < totalColumnIndexValues.length; i++) {
            int totalColumnIndexValue = totalColumnIndexValues[i];
            double total = 0.0d;
            for (int row = 0; row < rowCount; row++) {
                ArrayList<Object> values = dataArray.get(row);
                total += (double) values.get(totalColumnIndexValue);
            }
            totals[totalColumnIndexValue] = total;
        }
        for (int i = 0; i < totalColumnIndexValues.length; i++) {
            int totalColumnIndexValue = totalColumnIndexValues[i];
            dataArray.get(rowCount).set(totalColumnIndexValue, totals[totalColumnIndexValue]);
        }
    }
}
