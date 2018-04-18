package main.java;

public class TableLayout {
    private int layoutID;
    private String layoutName;
    private String layoutDate;
    private String layout;
    private boolean layoutInUse;

    public boolean isLayoutInUse() {
        return layoutInUse;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public String getLayout() {
        return layout;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public String getLayoutDate() {
        return layoutDate;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setLayoutDate(String layoutDate) {
        this.layoutDate = layoutDate;
    }

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    public void setLayoutInUse(boolean layoutInUse) {
        this.layoutInUse = layoutInUse;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }
}
