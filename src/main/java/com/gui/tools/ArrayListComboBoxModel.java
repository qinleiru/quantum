package com.gui.tools;

import javax.swing.*;
import java.util.ArrayList;

public class ArrayListComboBoxModel extends AbstractListModel implements ComboBoxModel {
    private  Object selectedItem;
    private ArrayList arrayList;
    public ArrayListComboBoxModel(ArrayList arrayList){
        this.arrayList=arrayList;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem=anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getSize() {
        return arrayList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return arrayList.get(index);
    }
}
