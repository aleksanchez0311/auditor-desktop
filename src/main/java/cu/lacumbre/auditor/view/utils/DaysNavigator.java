package cu.lacumbre.auditor.view.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.components.JSpinField;
import cu.lacumbre.auditor.EntitySelector;

import cu.lacumbre.utils.Timing;

public final class DaysNavigator extends JPanel {

    private Timing timing;
    private boolean onLoad = true;
    private boolean waitingMonthUpdate = false;
    private boolean waitingYearUpdate = false;
    private DaysNavigableView invoker;

    public DaysNavigator() {
        this.invoker = null;
        this.timing = new Timing();
        initComponents();
    }

    public void setInvoker(DaysNavigableView invoker) {
        this.invoker = invoker;
        this.timing = invoker.getTiming();
        onLoad = false;
        waitingMonthUpdate = true;
        waitingYearUpdate = true;
        dcDayValue.setValue(timing.getLocalDate().getDayOfMonth());
        dcMonthValue.setMonth(timing.getLocalDate().getMonthValue() - 1);
        dcYearValue.setYear(timing.getLocalDate().getYear());
        updateInvokersView();
        waitingMonthUpdate = false;
        waitingYearUpdate = false;
    }

    public DaysNavigableView getInvoker() {
        return invoker;
    }

    public int getDay() {
        return dcDayValue.getValue();
    }

    public int getMonth() {
        return dcMonthValue.getMonth() + 1;
    }

    public int getYear() {
        return dcYearValue.getYear();
    }

    public void setTiming(Timing timing) {
        this.timing = timing;
        invoker.setTiming(timing);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDateKey = new JLabel();
        dcDayValue = new JSpinField();
        dcMonthValue = new JMonthChooser();
        dcYearValue = new JYearChooser();
        btnSetPreviousPeriod = new JButton();
        btnSetCurrentsPeriod = new JButton();
        btnSetNextPeriod = new JButton();

        setMaximumSize(new Dimension(624, 40));
        setMinimumSize(new Dimension(624, 40));
        setPreferredSize(new Dimension(624, 40));

        lblDateKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblDateKey.setText("Fecha:");
        add(lblDateKey);

        dcDayValue.setMaximum(31);
        dcDayValue.setMaximumSize(new Dimension(40, 29));
        dcDayValue.setMinimum(1);
        dcDayValue.setMinimumSize(new Dimension(40, 29));
        dcDayValue.setPreferredSize(new Dimension(40, 29));
        dcDayValue.setValue(1);
        dcDayValue.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                dcDayValuePropertyChange(evt);
            }
        });
        add(dcDayValue);

        dcMonthValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        dcMonthValue.setMaximumSize(new Dimension(130, 29));
        dcMonthValue.setMinimumSize(new Dimension(130, 29));
        dcMonthValue.setMonth(0);
        dcMonthValue.setPreferredSize(new Dimension(130, 29));
        dcMonthValue.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                dcMonthValuePropertyChange(evt);
            }
        });
        add(dcMonthValue);

        dcYearValue.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        dcYearValue.setMaximumSize(new Dimension(56, 29));
        dcYearValue.setMinimumSize(new Dimension(56, 29));
        dcYearValue.setPreferredSize(new Dimension(56, 29));
        dcYearValue.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                dcYearValuePropertyChange(evt);
            }
        });
        add(dcYearValue);

        btnSetPreviousPeriod.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSetPreviousPeriod.setText("Día Anterior");
        btnSetPreviousPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSetPreviousPeriodActionPerformed(evt);
            }
        });
        add(btnSetPreviousPeriod);

        btnSetCurrentsPeriod.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSetCurrentsPeriod.setText("Este Día");
        btnSetCurrentsPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSetCurrentsPeriodActionPerformed(evt);
            }
        });
        add(btnSetCurrentsPeriod);

        btnSetNextPeriod.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSetNextPeriod.setText("Día Siguiente");
        btnSetNextPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSetNextPeriodActionPerformed(evt);
            }
        });
        add(btnSetNextPeriod);
    }// </editor-fold>//GEN-END:initComponents

    private void dcDayValuePropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_dcDayValuePropertyChange
        if (!onLoad)
            if (!waitingMonthUpdate)
                if (!waitingYearUpdate)
                    updateInvokersView();
    }//GEN-LAST:event_dcDayValuePropertyChange

    private void dcMonthValuePropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_dcMonthValuePropertyChange
        if (!onLoad)
            if (!waitingMonthUpdate)
                if (!waitingYearUpdate)
                    updateInvokersView();
    }//GEN-LAST:event_dcMonthValuePropertyChange

    private void dcYearValuePropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_dcYearValuePropertyChange
        if (!onLoad)
            if (!waitingYearUpdate)
                updateInvokersView();
    }//GEN-LAST:event_dcYearValuePropertyChange

    private void btnSetPreviousPeriodActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSetPreviousPeriodActionPerformed
        waitingMonthUpdate = true;
        waitingYearUpdate = true;
        LocalDate previousDay = timing.minusDays(1);
        dcDayValue.setValue(previousDay.getDayOfMonth());
        dcMonthValue.setMonth(previousDay.getMonthValue() - 1 );
        dcYearValue.setYear(previousDay.getYear());
        updateInvokersView();
        waitingMonthUpdate = false;
        waitingYearUpdate = false;
    }//GEN-LAST:event_btnSetPreviousPeriodActionPerformed

    private void btnSetCurrentsPeriodActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSetCurrentsPeriodActionPerformed
        waitingMonthUpdate = true;
        waitingYearUpdate = true;
        setTiming(new Timing(EntitySelector.currentEntity.getCurrentDay()));
        LocalDate currentDay = timing.getLocalDate();
        dcDayValue.setValue(currentDay.getDayOfMonth());
        dcMonthValue.setMonth(currentDay.getMonthValue() - 1);
        dcYearValue.setYear(currentDay.getYear());
        updateInvokersView();
        waitingMonthUpdate = false;
        waitingYearUpdate = false;
    }//GEN-LAST:event_btnSetCurrentsPeriodActionPerformed

    private void btnSetNextPeriodActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSetNextPeriodActionPerformed
        waitingMonthUpdate = true;
        waitingYearUpdate = true;
        LocalDate nextDay = timing.plusDays(1);
        dcDayValue.setValue(nextDay.getDayOfMonth());
        dcMonthValue.setMonth(nextDay.getMonthValue() - 1);
        dcYearValue.setYear(nextDay.getYear());
        waitingMonthUpdate = false;
        waitingYearUpdate = false;
        updateInvokersView();
    }//GEN-LAST:event_btnSetNextPeriodActionPerformed

    public void updateInvokersView() {
        int day = getDay();
        int month = getMonth();
        int year = getYear();
        setTiming(new Timing(LocalDate.of(year, month, day)));
        invoker.updateView(day, month, year);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnSetCurrentsPeriod;
    private JButton btnSetNextPeriod;
    private JButton btnSetPreviousPeriod;
    private JSpinField dcDayValue;
    private JMonthChooser dcMonthValue;
    private JYearChooser dcYearValue;
    private JLabel lblDateKey;
    // End of variables declaration//GEN-END:variables

}
