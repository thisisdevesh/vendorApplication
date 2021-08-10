package com.gts.coordinator.dao;

/**
 * Created by GTS02 on 08-Nov-2016 17:08.
 */

public class PkgServiceItem {
    private long id ;
    private String  name;
    private boolean isSelected ;

    public PkgServiceItem(long id, String name, boolean isSelected)
    {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "["+id+"|"+name+"|"+isSelected+"]";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
