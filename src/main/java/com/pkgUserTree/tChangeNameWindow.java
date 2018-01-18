package com.pkgUserTree;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.*;

/**
 * Created by kalistrat on 19.01.2018.
 */
public class tChangeNameWindow extends Window {

    Button SaveButton;
    TextField EditTextField;
    tTreeContentLayout iTreeContentLayout;
    int iLeafId;
    Label iTopLabel;


    public tChangeNameWindow(int eLeafId
            ,tTreeContentLayout eParentContentLayout
                             ,Label eTopLabel
                             ,TextField ChangingTextField

    ){
        iLeafId = eLeafId;
        iTreeContentLayout = eParentContentLayout;
        iTopLabel = eTopLabel;


        this.setIcon(VaadinIcons.PENCIL);
        this.setCaption(" Введите новое наименование");

        EditTextField = new TextField("Новое наименование");
        EditTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        EditTextField.setValue(
                iTreeContentLayout.GetLeafNameById(iLeafId)
        );


        SaveButton = new Button("Сохранить");

        SaveButton.setData(this);
        SaveButton.addStyleName(ValoTheme.BUTTON_SMALL);
        SaveButton.setIcon(FontAwesome.SAVE);



        HorizontalLayout ButtonsLayout = new HorizontalLayout(
                SaveButton
        );

        ButtonsLayout.setSizeUndefined();
        ButtonsLayout.setSpacing(true);

        VerticalLayout MessageLayout = new VerticalLayout(
                EditTextField
        );
        MessageLayout.setSpacing(true);
        MessageLayout.setWidth("320px");
        MessageLayout.setHeightUndefined();
        MessageLayout.setMargin(true);
        MessageLayout.setComponentAlignment(EditTextField, Alignment.MIDDLE_CENTER);
        MessageLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        VerticalLayout WindowContentLayout = new VerticalLayout(
                MessageLayout
                ,ButtonsLayout
        );
        WindowContentLayout.setSizeUndefined();
        WindowContentLayout.setSpacing(true);
        WindowContentLayout.setMargin(true);
        WindowContentLayout.setComponentAlignment(ButtonsLayout, Alignment.BOTTOM_CENTER);

        this.setContent(WindowContentLayout);
        this.setSizeUndefined();
        this.setModal(true);
    }


    public void renameUserLeaf(String qUserLog,int qLeafId,String qNewLeafName){
        try {

            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            CallableStatement NewLeafNameStmt = Con.prepareCall("{call p_rename_leaf(?, ?, ?)}");
            NewLeafNameStmt.setString(1, qUserLog);
            NewLeafNameStmt.setInt(2, qLeafId);
            NewLeafNameStmt.setString(3, qNewLeafName);
            NewLeafNameStmt.execute();
            Con.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

    }
}
