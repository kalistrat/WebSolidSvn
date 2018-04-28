package com;

import com.pkgChatLayout.TempClass;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.sql.*;


/**
 * Created by kalistrat on 19.12.2017.
 */
public class loginView extends CustomComponent implements View {

    public static final String NAME = "Login";
    Button LogOnButton = new Button("Войти");
    Button RemindButton = new Button("Напомнить пароль");

    TextField LogInField = new TextField("Имя пользователя");
    PasswordField PassField = new PasswordField("Пароль");

    public loginView(){
        setSizeFull();
        VerticalLayout LoginViewLayOut = new VerticalLayout();


        VerticalLayout LoginBox = new VerticalLayout();
        LoginBox.setSpacing(true);

        LogInField.setWidth("310px");
        LogInField.setIcon(FontAwesome.USER);
        PassField.setWidth("310px");
        PassField.setIcon(FontAwesome.KEY);


        LoginBox.addComponent(new Label());
        LoginBox.addComponent(LogInField);
        LoginBox.addComponent(PassField);

        LoginBox.setComponentAlignment(LogInField,Alignment.MIDDLE_CENTER);
        LoginBox.setComponentAlignment(PassField,Alignment.MIDDLE_CENTER);

        HorizontalLayout ButtonsBox = new HorizontalLayout();
        ButtonsBox.setSpacing(true);
        ButtonsBox.setSizeUndefined();

        LogOnButton.setSizeUndefined();
        LogOnButton.setIcon(FontAwesome.SIGN_IN);

        LogOnButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String username = LogInField.getValue();
                String password = staticMethods.sha256(PassField.getValue());
                String db_Password = "";

                try {

                    Class.forName(staticMethods.JDBC_DRIVER);
                    Connection conn = DriverManager.getConnection(
                            staticMethods.DB_URL
                            ,staticMethods.USER
                            ,staticMethods.PASS
                    );


                    CallableStatement CheckUserStmt = conn.prepareCall("{? = call f_get_user_password(?)}");
                    CheckUserStmt.registerOutParameter (1, Types.VARCHAR);
                    CheckUserStmt.setString(2, username);
                    CheckUserStmt.execute();
                    db_Password = CheckUserStmt.getString(1);

                    CallableStatement GetUserIdStmt = conn.prepareCall("{? = call F_GET_USERID(?)}");
                    GetUserIdStmt.registerOutParameter(1, Types.INTEGER);
                    GetUserIdStmt.setString(2, username);
                    GetUserIdStmt.execute();

                    String OsName = System.getProperty("os.name").toLowerCase();

                    if (OsName.indexOf("windows")>=0)
                    {
                    TempClass.FolderSeparateCharacter = "\\";
                    }
                    else //Assuming Unix
                    {
                    TempClass.FolderSeparateCharacter = "/";
                    }

                    TempClass.current_user_id = GetUserIdStmt.getInt(1);

                    conn.close();
                } catch(SQLException SQLe){
                    //Handle errors for JDBC
                    SQLe.printStackTrace();
                }catch(Exception e1){
                    //Handle errors for Class.forName
                    e1.printStackTrace();
                }

                if (db_Password == null) {
                    db_Password = "";
                }

                //System.out.println("password sha : " + password);

                if (db_Password.equals(password) && !password.equals("")) {

                    // Store the current user in the service session
                    getSession().setAttribute("user", username);

                    // Navigate to main view
                    getUI().getNavigator().navigateTo(mainView.NAME);//

                } else {

                    PassField.setValue("");
                    LogInField.setValue("");
                    PassField.focus();
                    Notification.show("Ошибка авторизации!",
                            "Логин или пароль неверен",
                            Notification.Type.TRAY_NOTIFICATION);

                }
            }
        });

        RemindButton.setIcon(VaadinIcons.QUESTION_CIRCLE);

        ButtonsBox.addComponent(LogOnButton);
        ButtonsBox.addComponent(RemindButton);
        ButtonsBox.setComponentAlignment(LogOnButton,Alignment.BOTTOM_LEFT);
        ButtonsBox.setComponentAlignment(RemindButton,Alignment.BOTTOM_RIGHT);

        LoginBox.addComponent(ButtonsBox);
        LoginBox.setComponentAlignment(ButtonsBox,Alignment.MIDDLE_CENTER);
        LoginBox.setSizeUndefined();

        LoginViewLayOut.addComponent(LoginBox);
        LoginViewLayOut.setComponentAlignment(LoginBox,Alignment.MIDDLE_CENTER);

        LoginViewLayOut.setSizeFull();

        LoginViewLayOut.setExpandRatio(LoginBox,2);



        setCompositionRoot(LoginViewLayOut);

    }

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // focus the username field when user arrives to the login view
        LogInField.focus();
    }
}
