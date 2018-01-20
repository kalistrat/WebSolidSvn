package com.pkgUserTree;

/**
 * Created by kalistrat on 18.01.2018.
 */
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;


public class tLeafButton extends Button {

    public tLeafButton(int eButtonLeafId,tTreeContentLayout iParentContentLayout){

        String iButtonCaption = (String) iParentContentLayout.itTree.getItem(eButtonLeafId).getItemProperty(4).getValue();
        String iButtonIconCode = (String) iParentContentLayout.itTree.getItem(eButtonLeafId).getItemProperty(5).getValue();
        Integer iUserDeviceId = (Integer) iParentContentLayout.itTree.getItem(eButtonLeafId).getItemProperty(6).getValue();

        this.setCaption(doStringWrap(iButtonCaption));

        if (iButtonIconCode.equals("FOLDER")) {
            this.setIcon(VaadinIcons.FOLDER);
        }
        if (iButtonIconCode.equals("PART")) {
            this.setIcon(FontAwesome.CUBE);
        }
        if (iButtonIconCode.equals("ASSEMBLY")) {
            this.setIcon(VaadinIcons.CUBES);
        }
        if (iButtonIconCode.equals("DOCUMENT")) {
            this.setIcon(VaadinIcons.FILE);
        }
        if (iButtonIconCode.equals("COMPLEX")) {
            this.setIcon(VaadinIcons.QUESTION_CIRCLE);
        }

        this.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                iParentContentLayout.tTreeContentLayoutRefresh(eButtonLeafId,iUserDeviceId);
            }
        });


        this.setSizeUndefined();
        this.addStyleName("ButtonHugeIcon");
        this.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
        this.addStyleName(ValoTheme.BUTTON_LINK);


    }

    private static String doStringWrap(String inputStr){
        StringBuffer s0 = new StringBuffer(inputStr.trim());
        List<Integer> sIndxList = new ArrayList<Integer>();
        int k = 1;
        int sPacePos = 0;
        int csym = 0;
        int csymMax = 4;

        if (s0.substring(0, 1).equals("\n")) {
            s0.deleteCharAt(0);
        }

        if (s0.indexOf(" ") != -1) {

            for (int i = 0; i < s0.length(); i++) {

                if ((s0.substring(i, i + 1).equals(" ")) && (csym > csymMax)) {
                    sPacePos = i;
                    csym = 0;
                } else {
                    csym = csym + 1;
                }

                if ((!sIndxList.contains(sPacePos)) && (csym == 0)) {
                    k = k + 1;
                    sIndxList.add(sPacePos);
                }

            }

            for (Integer ii : sIndxList) {
                s0.setCharAt(ii, '\n');
            }

        } else {

            csymMax = 10;

            for (int i = 0; i < s0.length(); i++) {
                if (csym < csymMax) {
                    csym = csym + 1;
                } else {
                    csym = 0;
                    s0.insert(i,'\n');
                }
            }

        }

        if (s0.substring(s0.length()-1, s0.length()).equals("\n")) {
            s0.deleteCharAt(s0.length());
        }

        return s0.toString();
    }

}
