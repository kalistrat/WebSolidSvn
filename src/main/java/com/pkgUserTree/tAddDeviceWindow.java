package com.pkgUserTree;

import com.vaadin.data.Item;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.*;

/**
 * Created by kalistrat on 19.01.2018.
 */
public class tAddDeviceWindow extends Window {

    Button SaveButton;
    Button CancelButton;
    TextField EditTextField;
    TextField InWriteTopicName;
    tTreeContentLayout iTreeContentLayout;
    int iLeafId;

    int iNewTreeId;
    int iNewLeafId;
    int iNewUserDeviceId;
    String iNewIconCode;

    NativeSelect DeviceActionType;
    String LastActionTypeValue;

    TextField ChildTopicField;
    Label RootTopicName;
    HorizontalLayout InTopicNameField;

    public tAddDeviceWindow(int eLeafId
            ,tTreeContentLayout eParentContentLayout
    ){
        iLeafId = eLeafId;
        iTreeContentLayout = eParentContentLayout;

        iNewTreeId = 0;
        iNewLeafId = 0;


        this.setIcon(VaadinIcons.PLUG);
        this.setCaption(" Добавление нового устройства");

        EditTextField = new TextField("Наименование устройства :");
        EditTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

        ChildTopicField = new TextField();
        ChildTopicField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        ChildTopicField.setValue("");

        RootTopicName = new Label();
        RootTopicName.addStyleName("FormTextLabel");
        setRootTopicData(iLeafId, iTreeContentLayout.iUserLog);

        InTopicNameField = new HorizontalLayout(
                RootTopicName
                ,ChildTopicField
        );
        InTopicNameField.setCaption("mqtt-топик для данных :");
        //InTopicNameField.setEnabled(false);

        DeviceActionType = new NativeSelect("Тип устройства :");
        getActionTypeData();
        DeviceActionType.setNullSelectionAllowed(false);
        DeviceActionType.select(LastActionTypeValue);
        DeviceActionType.addStyleName("SelectFont");


        SaveButton = new Button("Сохранить");

        SaveButton.setData(this);
        SaveButton.addStyleName(ValoTheme.BUTTON_SMALL);
        SaveButton.setIcon(FontAwesome.SAVE);


        CancelButton = new Button("Отменить");

        CancelButton.setData(this);
        CancelButton.addStyleName(ValoTheme.BUTTON_SMALL);
        CancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().removeWindow((tAddDeviceWindow) clickEvent.getButton().getData());
            }
        });

        HorizontalLayout ButtonsLayout = new HorizontalLayout(
                SaveButton
                ,CancelButton
        );

        ButtonsLayout.setSizeUndefined();
        ButtonsLayout.setSpacing(true);

        FormLayout IniDevParamLayout = new FormLayout(
                EditTextField
                ,DeviceActionType
                ,InTopicNameField
        );
        IniDevParamLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        IniDevParamLayout.setSizeUndefined();
        IniDevParamLayout.addStyleName("FormFont");
        IniDevParamLayout.setMargin(false);

        VerticalLayout MessageLayout = new VerticalLayout(
                IniDevParamLayout
        );
        MessageLayout.setSpacing(true);
        MessageLayout.setWidth("520px");
        MessageLayout.setHeightUndefined();
        MessageLayout.setMargin(new MarginInfo(true,false,true,false));
        MessageLayout.setComponentAlignment(IniDevParamLayout, Alignment.MIDDLE_CENTER);
        MessageLayout.addStyleName(ValoTheme.LAYOUT_CARD);

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
        //this.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
    }

    public void getActionTypeData(){

        try {
            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            String DataSql = "select at.action_type_name\n" +
                    "from action_type at";

            PreparedStatement DataStmt = Con.prepareStatement(DataSql);

            ResultSet DataRs = DataStmt.executeQuery();

            while (DataRs.next()) {
                LastActionTypeValue = DataRs.getString(1);
                DeviceActionType.addItem(DataRs.getString(1));
            }


            Con.close();

        } catch (SQLException se3) {
            //Handle errors for JDBC
            se3.printStackTrace();
        } catch (Exception e13) {
            //Handle errors for Class.forName
            e13.printStackTrace();
        }
    }

    public void setRootTopicData(int qParentLeafId, String qUserLog){

        try {
            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            String DataSql = "select udt.control_log\n" +
                    "from user_devices_tree udt\n" +
                    "join users u on u.user_id=udt.user_id\n" +
                    "where u.user_log = ?\n" +
                    "and udt.leaf_id = ?";

            PreparedStatement DataStmt = Con.prepareStatement(DataSql);
            DataStmt.setString(1,qUserLog);
            DataStmt.setInt(2,qParentLeafId);

            ResultSet DataRs = DataStmt.executeQuery();

            while (DataRs.next()) {
                RootTopicName.setValue("/"+DataRs.getString(1)+"/");
            }


            Con.close();

        } catch (SQLException se3) {
            //Handle errors for JDBC
            se3.printStackTrace();
        } catch (Exception e13) {
            //Handle errors for Class.forName
            e13.printStackTrace();
        }
    }


    public void addUserDevice(
        int qParentLeafId
        , String qDeviceName
        , String qUserLog
        , String qActionTypeName
        , String qInTopicName
    ){
        try {

            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            CallableStatement addDeviceStmt = Con.prepareCall("{call p_add_user_device(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            addDeviceStmt.setInt(1, qParentLeafId);
            addDeviceStmt.setString(2, qDeviceName);
            addDeviceStmt.setString(3, qUserLog);
            addDeviceStmt.setString(4, qActionTypeName);
            addDeviceStmt.registerOutParameter(5, Types.INTEGER);
            addDeviceStmt.registerOutParameter(6, Types.INTEGER);
            addDeviceStmt.registerOutParameter(7, Types.VARCHAR);
            addDeviceStmt.registerOutParameter(8, Types.INTEGER);
            addDeviceStmt.setString(9, qInTopicName);

            addDeviceStmt.execute();

            iNewTreeId = addDeviceStmt.getInt(5);
            iNewLeafId = addDeviceStmt.getInt(6);
            iNewIconCode = addDeviceStmt.getString(7);
            iNewUserDeviceId = addDeviceStmt.getInt(8);

            Con.close();


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            //return "Ошибка JDBC";
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            //return "Ошибка Class.forName";
        }

    }

    public Integer isExistsTopicName(String qTopicName){
        Integer isE = 0;
        try {

            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            CallableStatement callStmt = Con.prepareCall("{? = call fIsExistsTopicName(?)}");
            callStmt.registerOutParameter(1, Types.INTEGER);
            callStmt.setString(2, qTopicName);
            callStmt.execute();

            isE =  callStmt.getInt(1);

            Con.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return isE;
    }

    public Integer GetSyncIntervalDays(int qUserDeviceId){
        Integer isE = 0;
        try {

            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            CallableStatement callStmt = Con.prepareCall("{? = call fGetSyncIntervalDays(?)}");
            callStmt.registerOutParameter(1, Types.INTEGER);
            callStmt.setInt(2, qUserDeviceId);
            callStmt.execute();

            isE =  callStmt.getInt(1);

            Con.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return isE;
    }

    public Integer addUserDeviceTask(
            int qUserDeviceId
            , String eTaskTypeName
            , int eTaskInterval
            , String eIntervalType
    ){
        Integer iTaskId = 0;
        try {

            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            CallableStatement addDeviceTaskStmt = Con.prepareCall("{call p_add_task(?, ?, ?, ?, ?, ?)}");
            addDeviceTaskStmt.setInt(1, qUserDeviceId);
            addDeviceTaskStmt.setString(2, eTaskTypeName);
            addDeviceTaskStmt.setInt(3, eTaskInterval);
            addDeviceTaskStmt.setString(4, eIntervalType);
            addDeviceTaskStmt.setNull(5,Types.VARCHAR);
            addDeviceTaskStmt.registerOutParameter(6, Types.INTEGER);

            addDeviceTaskStmt.execute();

            iTaskId = addDeviceTaskStmt.getInt(6);

            Con.close();


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            //return "Ошибка JDBC";
        }catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            //return "Ошибка Class.forName";
        }
        return iTaskId;

    }
}
