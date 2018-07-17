package com.pkgChatLayout;

import com.staticMethods;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Types;

/**
* Created by Dmitriy on 24.06.2018.
*/


public class MessageListLayout extends VerticalLayout
{
//Полный путь к загруженному файлу
String sfilepath;

//Имя загруженного файла
String sfilename;
Upload Upload1;

//Если True, то загруженный файл надо удалить
Boolean DeleteUploadedFile = false;

public MessageListLayout()
{
sfilepath = null;
sfilename = null;

setWidth("100%");
HorizontalLayout hlayout21 = new HorizontalLayout();
HorizontalLayout hlayout22 = new HorizontalLayout();
HorizontalLayout hlayout23 = new HorizontalLayout();

hlayout21.setWidth("100%");
hlayout22.setWidth("100%");
hlayout23.setWidth("100%");
hlayout23.addStyleName("hlayout-with-padding");
hlayout23.addStyleName("hlayout-with-borders");

/* hlayout21 */
/* hlayout21 */

/* hlayout22 */

MessageListTable MsgListTable1 = new MessageListTable();
TempClass.RelMessageListTable = MsgListTable1;
hlayout22.addComponent(MsgListTable1);

/* hlayout22 */

/* hlayout23 */

Button SendMessageButton = new Button("Отправить");
TextArea MessageTextArea = new TextArea();
MessageTextArea.setWidth("100%");
MessageTextArea.setRows(1);
//MessageTextArea.setSizeFull();

/* UploadReceiver - BEGIN */
class UploadReceiver implements Upload.Receiver, Upload.SucceededListener
,Upload.StartedListener
{

public void uploadStarted(Upload.StartedEvent event)
{
// Handled by Upload component
}

public void uploadSucceeded(Upload.SucceededEvent event)
{
// Handled by Upload component
}

public OutputStream receiveUpload(String filename, String mimeType)
{
if (filename.equals(""))
{
return new NullOutputStream();
}

/*
Если TempClass.NewMessageID <> 0, значит сообщение успешно добавлено в БД
и мы получили его ID

ID нужен для создания папки для файла.
*/

if (TempClass.NewMessageID !=0 )
{
// Find the application directory - src\main\webapp
String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
String uploadfolder = basepath + "\\uploads\\" + String.valueOf(TempClass.NewMessageID) ;

//Создаем папку для загрузки
File file1 = new File (uploadfolder);
file1.mkdir();
FileOutputStream fos;

try
{
sfilename = filename;
sfilepath = uploadfolder + "\\"  + filename;
File tempfile = new File(sfilepath);
fos = new FileOutputStream(tempfile);
return fos;
}

catch (final java.io.FileNotFoundException e)
{
Notification.show("Ошибка открытия файла","Ошибка открытия файла " + filename, Notification.Type.ERROR_MESSAGE);
return new NullOutputStream();
}
}
return null;
}

}

/* UploadReceiver - END */

UploadReceiver receiver = new UploadReceiver();
/* Upload - BEGIN */

Upload1 = new Upload("Upload Image Here", receiver);
Upload1.setImmediate(false);
Upload1.addSucceededListener(receiver);
Upload1.addStartedListener(receiver);
Upload1.addStyleName("HiddenUploadButton");
Upload1.setButtonCaption(null);

Upload1.addStartedListener(new Upload.StartedListener()
{
Integer MaximumFileUploadSizeInBytes = 10485760;

@Override public void uploadStarted(Upload.StartedEvent startedEvent)
{
if (startedEvent.getContentLength() > MaximumFileUploadSizeInBytes)
{
DeleteUploadedFile = true;
Upload1.interruptUpload();
Notification.show("Максимальный размер файла - 10Мб.",  null,   Notification.Type.HUMANIZED_MESSAGE);
/*
Удаление большого файла сделаем отдельным джобом/приложением
Если делать удаление в Upload1.addFinishedListener то получаем ошибки  NullPointerException NullOutputStreamException
*/
}
else
{
DeleteUploadedFile = false;
}
}
});

Upload1.addFailedListener(new Upload.FailedListener()
{
@Override public void uploadFailed(Upload.FailedEvent failedEvent)
{

if (sfilepath.equals(null))
{
return;
}

try
{
Connection con;
Class.forName(staticMethods.JDBC_DRIVER);
con = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
PreparedStatement Stmt1 = con.prepareCall("call solid.pkg_external_link.f_add_external_link(?,?,?,?)");
Stmt1.setInt(1, TempClass.NewMessageID);
Stmt1.setString(2, sfilepath);
Stmt1.setString(3, sfilename);

if (DeleteUploadedFile)
{
Stmt1.setString(4, "N");
}
else
{
Stmt1.setString(4, "Y");
}
Stmt1.execute();
con.close();
sfilepath = null;
sfilename = null;

DeleteUploadedFile = false;
MsgListTable1.UpdateMessagesList(TempClass.second_user_id);
}
catch (Exception exp)
{
exp.printStackTrace();
DeleteUploadedFile = false;
sfilepath = null;
sfilename = null;
}

}
});


Upload1.addSucceededListener(new Upload.SucceededListener()
{
@Override public void uploadSucceeded(Upload.SucceededEvent succeededEvent)
{
if (sfilepath.equals(null))
{
return;
}

try
{
Connection con;
Class.forName(staticMethods.JDBC_DRIVER);
con = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
PreparedStatement Stmt1 = con.prepareCall("call solid.pkg_external_link.f_add_external_link(?,?,?,?)");
Stmt1.setInt(1, TempClass.NewMessageID);
Stmt1.setString(2, sfilepath);
Stmt1.setString(3, sfilename);

if (DeleteUploadedFile)
{
Stmt1.setString(4, "N");
}
else
{
Stmt1.setString(4, "Y");
}

Stmt1.execute();
con.close();
sfilepath = null;
sfilename = null;
DeleteUploadedFile = false;
MsgListTable1.UpdateMessagesList(TempClass.second_user_id);
}
catch (Exception exp)
{
exp.printStackTrace();
DeleteUploadedFile = false;
sfilepath = null;
sfilename = null;
}
}
});
;

/* Upload - END */

HorizontalLayout MessageTextAreaLayout = new HorizontalLayout();
HorizontalLayout MessageTextAreaLayoutLeftLayout= new HorizontalLayout();
MessageTextAreaLayoutLeftLayout.setWidth("450px");
MessageTextArea.setHeight("70px");
MessageTextArea.setWidth ("400px");
MessageTextAreaLayoutLeftLayout.addComponent(MessageTextArea);

VerticalLayout MessageTextAreaLayoutRightLayout = new VerticalLayout();
MessageTextAreaLayoutRightLayout.setWidth("100px");
Upload1.setWidth("100px");
SendMessageButton.setWidth("100px");

MessageTextAreaLayoutRightLayout.addComponent(Upload1);
MessageTextAreaLayoutRightLayout.addComponent(SendMessageButton);
MessageTextAreaLayout.addComponent(MessageTextAreaLayoutLeftLayout);
MessageTextAreaLayout.addComponent(MessageTextAreaLayoutRightLayout);

hlayout23.addComponent(MessageTextAreaLayout);

SendMessageButton.addClickListener(new Button.ClickListener()
{
@Override public void buttonClick(Button.ClickEvent clickEvent)
{
String v_message_text = MessageTextArea.getValue();

if (TempClass.second_user_id == null)
{
Notification.show("Не выбран получатель сообщения",  null,   Notification.Type.HUMANIZED_MESSAGE);
return;
}

if (v_message_text.length() == 0)
{
Notification.show("Не указан текст сообщения",  null,   Notification.Type.HUMANIZED_MESSAGE);
return;
}

Integer v_from_user_id = TempClass.current_user_id;
Integer v_to_user_id = TempClass.second_user_id;
Connection con;

try
{
Class.forName(staticMethods.JDBC_DRIVER);
con = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
CallableStatement Stmt= con.prepareCall( "{? = call solid.pkg_messagelist.f_addmessage2(?,?,?)}");
Stmt.registerOutParameter(1, Types.INTEGER);
Stmt.setInt(2, v_from_user_id);
Stmt.setInt(3, v_to_user_id);
Stmt.setString(4, v_message_text);
Stmt.execute();
TempClass.NewMessageID =  Stmt.getInt(1);
con.close();
MessageTextArea.clear();
Upload1.submitUpload();
MsgListTable1.UpdateMessagesList(TempClass.second_user_id);
}
catch (Exception exp)
{
exp.printStackTrace();
TempClass.NewMessageID  = 0;
}

}
});

/* hlayout23 */

addComponent(hlayout21);
addComponent(hlayout22);
addComponent(hlayout23);

}

}
