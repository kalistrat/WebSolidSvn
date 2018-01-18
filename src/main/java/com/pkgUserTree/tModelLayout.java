package com.pkgUserTree;

/**
 * Created by kalistrat on 18.01.2018.
 */

import com.vaadin.ui.Label;

import com.vaadin.ui.VerticalLayout;



public class tModelLayout extends VerticalLayout {


    public tModelLayout(int eLeafId, tTreeContentLayout eParentContentLayout){

        Integer iUserDeviceId = (Integer) eParentContentLayout.itTree.getItem(eLeafId).getItemProperty(6).getValue();
        String iActionType = (String) eParentContentLayout.itTree.getItem(eLeafId).getItemProperty(7).getValue();
        String iLeafName = (String) eParentContentLayout.itTree.getItem(eLeafId).getItemProperty(4).getValue();
        //System.out.println("eLeafId: " + eLeafId);

        if (iActionType.equals("Измерительное устройство")) {
            //this.addComponent(new tDetectorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Деталь"));

        }
        if (iActionType.equals("Исполнительное устройство")) {
            //this.addComponent(new tActuatorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Сборка"));
        }
        if (iActionType.equals("Исполнительное устройство")) {
            //this.addComponent(new tActuatorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Файл"));
        }
        if (iActionType.equals("Исполнительное устройство")) {
            //this.addComponent(new tActuatorLayout(Integer.valueOf(iUserDeviceId),iLeafName,eLeafId,eParentContentLayout));
            this.addComponent(new Label("Комплекс"));
        }

    }

}
