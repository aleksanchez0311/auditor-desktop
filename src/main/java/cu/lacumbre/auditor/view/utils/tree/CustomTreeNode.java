package cu.lacumbre.auditor.view.utils.tree;

import cu.lacumbre.utils.MonthlyPeriod;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

public class CustomTreeNode implements TreeNode {

    private final CustomTreeNode parent;
    private final Object value;
    private final MonthlyPeriod period;
    private final String text;
    private ArrayList<CustomTreeNode> children;

    public CustomTreeNode(CustomTreeNode parent, LocalDate date, String text) {
        this.parent = parent;
        this.value = date;
        this.text = text;
        if (date != null) {
            this.period = new MonthlyPeriod((LocalDate) date);
        }else{
            this.period = null;
        }
    }

    public void addChild(CustomTreeNode newChild) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(newChild);
    }

    public Object getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public MonthlyPeriod getPeriod() {
        return period;
    }

    public ArrayList<CustomTreeNode> getChildren() {
        return children;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return parent.getChildren().lastIndexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(children);
    }

    @Override
    public String toString() {
        return text;
    }

}
