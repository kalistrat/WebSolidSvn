package com.pkgUserTree;

/**
 * Created by kalistrat on 18.01.2018.
 */

import com.vaadin.data.Item;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class tFolderPrefsFormLayout extends VerticalLayout {

    Button SaveButton;
    Button EditButton;
    int iLeafId;
    String iUserLog;

    TextField NameTextField;
    TextField OutTopicNameField;
    TextField MqttServerTextField;

    TextField DeviceLoginTextField;
    TextField DevicePassWordTextField;
    NativeSelect TimeZoneSelect;
    TextField TimeSyncInterval;
    CheckBox SLLCheck;
    String sCurrentDeviceLog;

    public tFolderPrefsFormLayout(int eLeafId, String eUserLog) {

        iLeafId = eLeafId;
        iUserLog = eUserLog;

        Label Header = new Label();
        Header.setContentMode(ContentMode.HTML);
        Header.setValue(VaadinIcons.FORM.getHtml() + "  " + "Параметры контроллера");
        Header.addStyleName(ValoTheme.LABEL_COLORED);
        Header.addStyleName(ValoTheme.LABEL_SMALL);

        SaveButton = new Button();
        SaveButton.setIcon(FontAwesome.SAVE);
        SaveButton.addStyleName(ValoTheme.BUTTON_SMALL);
        SaveButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        SaveButton.setEnabled(false);


        EditButton = new Button();
        EditButton.setIcon(VaadinIcons.EDIT);
        EditButton.addStyleName(ValoTheme.BUTTON_SMALL);
        EditButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

        EditButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                sCurrentDeviceLog = DeviceLoginTextField.getValue();
                SaveButton.setEnabled(true);
                EditButton.setEnabled(false);
                DeviceLoginTextField.setEnabled(true);
                DevicePassWordTextField.setEnabled(true);
                TimeZoneSelect.setEnabled(true);
                TimeSyncInterval.setEnabled(true);
            }
        });


        HorizontalLayout FormHeaderButtons = new HorizontalLayout(
                EditButton
                ,SaveButton
        );
        FormHeaderButtons.setSpacing(true);
        FormHeaderButtons.setSizeUndefined();

        HorizontalLayout FormHeaderLayout = new HorizontalLayout(
                Header
                , FormHeaderButtons
        );
        FormHeaderLayout.setWidth("100%");
        FormHeaderLayout.setHeightUndefined();
        FormHeaderLayout.setComponentAlignment(Header, Alignment.MIDDLE_LEFT);
        FormHeaderLayout.setComponentAlignment(FormHeaderButtons, Alignment.MIDDLE_RIGHT);

        NameTextField = new TextField("Наименование контроллера :");
        NameTextField.setEnabled(false);
        OutTopicNameField = new TextField("mqtt-топик для синхронизации времени :");
        OutTopicNameField.setEnabled(false);

        DeviceLoginTextField = new TextField("Логин контроллера :");
        DevicePassWordTextField = new TextField("Пароль контроллера :");
        MqttServerTextField = new TextField("mqtt-сервер :");

        TimeZoneSelect = new NativeSelect("Часовой пояс контроллера :");
        TimeZoneSelect.setNullSelectionAllowed(false);


        TimeSyncInterval = new TextField("Интервал синхронизации времени (в сутках) :");

        SLLCheck = new CheckBox("шифрование трафика (SSL)");
        SLLCheck.addStyleName(ValoTheme.CHECKBOX_SMALL);
        SLLCheck.setValue(false);


        DeviceLoginTextField.setEnabled(false);
        DevicePassWordTextField.setEnabled(false);
        MqttServerTextField.setEnabled(false);
        TimeZoneSelect.setEnabled(false);
        TimeSyncInterval.setEnabled(false);
        SLLCheck.setEnabled(false);

        setControlerParameters();


        FormLayout ControlerForm = new FormLayout(
                NameTextField
                , MqttServerTextField
                , DeviceLoginTextField
                , DevicePassWordTextField
                , OutTopicNameField
                , TimeZoneSelect
                , TimeSyncInterval
                , SLLCheck
        );

        ControlerForm.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        ControlerForm.addStyleName("FormFont");
        ControlerForm.setMargin(false);

        VerticalLayout ControlerFormLayout = new VerticalLayout(
                ControlerForm
        );
        ControlerFormLayout.addStyleName(ValoTheme.LAYOUT_CARD);
        ControlerFormLayout.setWidth("100%");
        ControlerFormLayout.setHeightUndefined();

        VerticalLayout ContentLayout = new VerticalLayout(
                FormHeaderLayout
                , ControlerFormLayout
        );
        ContentLayout.setSpacing(true);
        ContentLayout.setWidth("100%");
        ContentLayout.setHeightUndefined();

        this.addComponent(ContentLayout);
    }

    public void setControlerParameters(){

        try {
            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            String DataSql = "select udt.leaf_name\n" +
                    ",udt.time_topic\n" +
                    ",ser.vserver_ip mqqtt\n" +
                    ",udt.control_log\n" +
                    ",udt.control_pass\n" +
                    ",tz.timezone_value\n" +
                    ",udt.sync_interval sync_interval\n" +
                    ",(\n" +
                    "select mss.server_type\n" +
                    "from mqtt_servers mss\n" +
                    "where mss.server_id=ser.server_id\n" +
                    ") server_type\n" +
                    "from user_devices_tree udt\n" +
                    "join users u on u.user_id=udt.user_id\n" +
                    "join mqtt_servers ser on ser.server_id=udt.mqtt_server_id\n" +
                    "join timezones tz on tz.timezone_id=udt.timezone_id\n" +
                    "where udt.leaf_id = ?\n" +
                    "and u.user_log = ?";

            PreparedStatement DataStmt = Con.prepareStatement(DataSql);
            DataStmt.setInt(1,iLeafId);
            DataStmt.setString(2,iUserLog);

            ResultSet DataRs = DataStmt.executeQuery();

            while (DataRs.next()) {

                NameTextField.setValue(DataRs.getString(1));
                OutTopicNameField.setValue(DataRs.getString(2));
                MqttServerTextField.setValue(DataRs.getString(3));
                DeviceLoginTextField.setValue(DataRs.getString(4));
                //sCurrentDeviceLog = DataRs.getString(4);
                DevicePassWordTextField.setValue(DataRs.getString(5));
                TimeZoneSelect.select(DataRs.getString(6));
                if (DataRs.getInt(7) == 0){
                    TimeSyncInterval.setValue("");
                } else {
                    TimeSyncInterval.setValue(String.valueOf(DataRs.getInt(7)));
                }
                if (DataRs.getString(8).equals("ssl")) {
                    SLLCheck.setValue(true);
                }

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

    public void updateFolderPrefsFormData(
            int qLeafId
            ,String qUserLog
            ,String qDeviceLog
            ,String qDevicePass
            ,String qDevicePassSha
            ,String qTimeZone
            ,int qTimeSyncInt
    ){
        try {

            Class.forName(com.staticMethods.JDBC_DRIVER);
            Connection Con = DriverManager.getConnection(
                    com.staticMethods.DB_URL
                    , com.staticMethods.USER
                    , com.staticMethods.PASS
            );

            CallableStatement Stmt = Con.prepareCall("{call p_updateFolderPrefsFormData(?, ? ,?, ?, ?, ?, ?)}");
            Stmt.setInt(1, qLeafId);
            Stmt.setString(2, qUserLog);
            Stmt.setString(3, qDeviceLog);
            Stmt.setString(4, qDevicePass);
            Stmt.setString(5, qDevicePassSha);
            Stmt.setString(6, qTimeZone);
            Stmt.setInt(7, qTimeSyncInt);

            Stmt.execute();

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
