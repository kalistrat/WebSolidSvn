package com.pkgUserTree;

/**
 * Created by kalistrat on 18.01.2018.
 */

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;



public class tModelLayout extends VerticalLayout {
    Integer iUserModelId;
    String iModelType;
    public tModelLayout(int eLeafId, tTreeContentLayout eParentContentLayout){

        iUserModelId = (Integer) eParentContentLayout.itTree.getItem(eLeafId).getItemProperty(6).getValue();
        iModelType = (String) eParentContentLayout.itTree.getItem(eLeafId).getItemProperty(7).getValue();
        String iLeafName = (String) eParentContentLayout.itTree.getItem(eLeafId).getItemProperty(4).getValue();


        if (iModelType.equals("PART")) {
            //this.addComponent(new tDetectorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Деталь" + iUserModelId));

        }
        if (iModelType.equals("ASSEMBLY")) {
            //this.addComponent(new tActuatorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Сборка" + iUserModelId));
        }
        if (iModelType.equals("DOCUMENT")) {
            //this.addComponent(new tActuatorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Документ" + iUserModelId));
        }
        if (iModelType.equals("COMPLEX")) {
            //this.addComponent(new tActuatorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Комплекс" + iUserModelId));
        }

    }

}
