package com;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;


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
                String password = PassField.getValue();
                String db_Password = "7";

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
