package com.pkgUserTree;

/**
 * Created by kalistrat on 18.01.2018.
 */
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;


public class tLeafButtonLayout extends VerticalLayout {
    tLeafButton LeafButton;
    public tLeafButtonLayout(int iButtonLeafId,tTreeContentLayout iParentContentLayout){

        LeafButton = new tLeafButton(iButtonLeafId, iParentContentLayout);
        this.addComponent(this.LeafButton);
        //this.setMargin(true);
        this.setSpacing(true);
        this.setComponentAlignment(this.LeafButton, Alignment.MIDDLE_CENTER);
        this.setSizeUndefined();
    }
}