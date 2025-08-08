package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.utils.Timing;

public interface PeriodsNavigableView {

    public Timing getTiming();

    public void setTiming(Timing newTiming);

    public void updateView(int month, int year);
    
    public void export();

}
