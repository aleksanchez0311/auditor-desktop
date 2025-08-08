package cu.lacumbre.auditor.view.utils;

import cu.lacumbre.utils.Timing;

public interface DaysNavigableView {

    public Timing getTiming();

    public void setTiming(Timing newTiming);

    public void updateView(int day, int month, int year);
    
}
