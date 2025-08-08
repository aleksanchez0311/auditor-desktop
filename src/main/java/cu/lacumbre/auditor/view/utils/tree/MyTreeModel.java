package cu.lacumbre.auditor.view.utils.tree;

import cu.lacumbre.auditor.EntitySelector;
import cu.lacumbre.auditor.model.Entity;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class MyTreeModel implements TreeModel {

    private final CustomTreeNode treeRoot;
    private CustomTreeNode currentLeaf;
    public static final int MONTH_LEAVES = 0;
    public static final int DAY_LEAVES = 1;
    public final Entity entity = EntitySelector.currentEntity;

    public MyTreeModel(int type) {
        switch (type) {
            case MONTH_LEAVES:
                treeRoot = createForMonthLeaves();
                break;
            case DAY_LEAVES:
                treeRoot = createForDaysLeaves();
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public Object getRoot() {
        return treeRoot;
    }

    public CustomTreeNode getCurrentLeaf() {
        return currentLeaf;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((CustomTreeNode) parent).getChildren().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((CustomTreeNode) parent).getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((CustomTreeNode) node).isLeaf();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((CustomTreeNode) parent).getIndex((TreeNode) child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }

    public CustomTreeNode getTreeRoot() {
        return treeRoot;
    }

    private CustomTreeNode createForMonthLeaves() {
        CustomTreeNode root = new CustomTreeNode(null, null, "Períodos");
        LocalDate start = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate now = LocalDate.now();
        LocalDate temp = start;
        int yearCount = 1;
        while (temp.getYear()
                < now.getYear()) {
            temp = temp.plusYears(1);
            yearCount++;
        }
        for (int yearIndex = 0;
                yearIndex < yearCount;
                yearIndex++) {
            CustomTreeNode year = new CustomTreeNode(root, null, start.getYear() + yearIndex + "");
            for (int monthIndex = 0; monthIndex < ((start.getYear() + yearIndex) == now.getYear() ? now.getMonthValue() : 12); monthIndex++) {
                LocalDate value = LocalDate.of(start.getYear() + yearIndex, monthIndex + 1, 1);
                CustomTreeNode month = new CustomTreeNode(year, value, Month.of(monthIndex + 1).getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase());
                year.addChild(month);
                if (value.getYear() == entity.getCurrentDay().getYear() && value.getMonth() == entity.getCurrentDay().getMonth()) {
                    this.currentLeaf = month;
                }
            }
            root.addChild(year);
        }
        return root;
    }

    private CustomTreeNode createForDaysLeaves() {
        CustomTreeNode treeRoot = new CustomTreeNode(null, null, "Periodos");
        LocalDate start = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate now = LocalDate.now();
        LocalDate temp = start;
        int yearCount = 1;
        while (temp.getYear() < now.getYear()) {
            temp = temp.plusYears(1);
            yearCount++;
        }
        LocalDate currentDate = start;
        for (int yearIndex = 0; yearIndex < yearCount; yearIndex++) {
            CustomTreeNode year = new CustomTreeNode(treeRoot, null, currentDate.getYear() + "");
            for (int monthIndex = 0; monthIndex < ((start.getYear() + yearIndex) == now.getYear() ? now.getMonthValue() : 12); monthIndex++) {
                int monthSize = currentDate.getMonth().length(currentDate.isLeapYear());
                CustomTreeNode month = new CustomTreeNode(year, null, currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase());
                for (int dayIndex = 0; dayIndex < (((start.getYear() + yearIndex) == now.getYear()) && (currentDate.getMonthValue() == now.getMonthValue()) ? currentDate.getDayOfMonth() : monthSize); dayIndex++) {
                    if (!currentDate.isAfter(now)) {
                        CustomTreeNode day = new CustomTreeNode(month, currentDate, currentDate.getDayOfMonth() + "");
                        month.addChild(day);
                        currentDate = currentDate.plusDays(1);
                        if (currentDate.isEqual(entity.getCurrentDay())) {
                            this.currentLeaf = day;
                        }
                    }
                }
                year.addChild(month);
            }
            treeRoot.addChild(year);
        }
        return treeRoot;
    }

}
