package cu.lacumbre.auditor.view.utils;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.utils.Logger;
import cu.lacumbre.utils.Timing;
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

public class PeriodsNavigator extends JPanel {

    protected Timing timing;
    protected boolean onLoad = true;
    protected boolean waitingYearUpdate = false;
    protected PeriodsNavigableView invoker;

    public PeriodsNavigator() {
        this.invoker = null;
        this.timing = new Timing();
        initComponents();
    }

    public void setInvoker(PeriodsNavigableView invoker) {
        this.invoker = invoker;
        this.timing = invoker.getTiming();
        onLoad = false;
        waitingYearUpdate = true;
        dcMonthValue.setMonth(timing.getLocalDate().getMonthValue() - 1);
        dcYearValue.setYear(timing.getLocalDate().getYear());
        updateView();
        waitingYearUpdate = false;
    }

    public PeriodsNavigableView getInvoker() {
        return invoker;
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
        dcMonthValue = new JMonthChooser();
        dcYearValue = new JYearChooser();
        btnSetPreviousPeriod = new JButton();
        btnSetCurrentsPeriod = new JButton();
        btnSetNextPeriod = new JButton();

        setMaximumSize(new Dimension(787, 40));
        setMinimumSize(new Dimension(787, 40));
        setPreferredSize(new Dimension(787, 40));

        lblDateKey.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        lblDateKey.setText("Periodo:");
        lblDateKey.setMaximumSize(new Dimension(58, 29));
        lblDateKey.setMinimumSize(new Dimension(58, 29));
        lblDateKey.setPreferredSize(new Dimension(58, 29));
        add(lblDateKey);

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
        btnSetPreviousPeriod.setText("Periodo Anterior");
        btnSetPreviousPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSetPreviousPeriodActionPerformed(evt);
            }
        });
        add(btnSetPreviousPeriod);

        btnSetCurrentsPeriod.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSetCurrentsPeriod.setText("Este Periodo");
        btnSetCurrentsPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSetCurrentsPeriodActionPerformed(evt);
            }
        });
        add(btnSetCurrentsPeriod);

        btnSetNextPeriod.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        btnSetNextPeriod.setText("Periodo Siguiente");
        btnSetNextPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSetNextPeriodActionPerformed(evt);
            }
        });
        add(btnSetNextPeriod);
    }// </editor-fold>//GEN-END:initComponents

    private void dcMonthValuePropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_dcMonthValuePropertyChange
        if (!onLoad) {
            if (!waitingYearUpdate) {
                if (!(evt.getPropertyName().equals("graphicsConfiguration") || evt.getPropertyName().equals("ancestor"))) {
                    Logger.getInstance().printLog(evt.getPropertyName(), false);
                    updateView();
                }
            }
        }
    }//GEN-LAST:event_dcMonthValuePropertyChange

    private void dcYearValuePropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_dcYearValuePropertyChange
        if (!onLoad) {
            if (!waitingYearUpdate) {
                if (!(evt.getPropertyName().equals("graphicsConfiguration") || evt.getPropertyName().equals("ancestor"))) {
                    Logger.getInstance().printLog(evt.getPropertyName(), false);
                    updateView();
                }
            }
        }
    }//GEN-LAST:event_dcYearValuePropertyChange

    private void btnSetPreviousPeriodActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSetPreviousPeriodActionPerformed
        waitingYearUpdate = true;
        LocalDate previousMonth = timing.minusMonths(1);
        dcMonthValue.setMonth(previousMonth.getMonthValue() - 1);
        dcYearValue.setYear(previousMonth.getYear());
        updateView();
        waitingYearUpdate = false;
    }//GEN-LAST:event_btnSetPreviousPeriodActionPerformed

    private void btnSetCurrentsPeriodActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSetCurrentsPeriodActionPerformed
        waitingYearUpdate = true;
        setTiming(new Timing(EntitySelector.currentEntity.getCurrentDay()));
        LocalDate currentMonth = timing.getLocalDate();
        dcMonthValue.setMonth(currentMonth.getMonthValue() - 1);
        dcYearValue.setYear(currentMonth.getYear());
        updateView();
        waitingYearUpdate = false;
    }//GEN-LAST:event_btnSetCurrentsPeriodActionPerformed

    private void btnSetNextPeriodActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSetNextPeriodActionPerformed
        waitingYearUpdate = true;
        LocalDate nextMonth = timing.plusMonths(1);
        dcMonthValue.setMonth(nextMonth.getMonthValue() - 1);
        dcYearValue.setYear(nextMonth.getYear());
        updateView();
        waitingYearUpdate = false;
    }//GEN-LAST:event_btnSetNextPeriodActionPerformed

    public void updateView() {
        int month = getMonth();
        int year = getYear();
        setTiming(new Timing(LocalDate.of(year, month, 1)));
        invoker.updateView(month, year);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnSetCurrentsPeriod;
    private JButton btnSetNextPeriod;
    private JButton btnSetPreviousPeriod;
    private JMonthChooser dcMonthValue;
    private JYearChooser dcYearValue;
    private JLabel lblDateKey;
    // End of variables declaration//GEN-END:variables

}
