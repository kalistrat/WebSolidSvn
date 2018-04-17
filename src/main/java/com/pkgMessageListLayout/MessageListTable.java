package com.pkgMessageListLayout;

import com.staticMethods;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;

import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

/**
* Created by Dmitriy on 07.01.2018.
*/

public class MessageListTable extends Table
{
private Integer RecordCount;

public MessageListTable()
{
addContainerProperty("MessageListTableColumn" , MessageItem.class, null);
RecordCount = 0;
setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
addStyleName(ValoTheme.TABLE_BORDERLESS);
//addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
//setSizeFull();
setWidth("100%");
}

public Integer GetRecordCount()
{
return RecordCount;
}

public void AddMessage(MessageItem NewMessage)
{
addItem(new Object[]{NewMessage}, RecordCount + 1);
RecordCount = RecordCount + 1;
setPageLength(RecordCount);
}

public void UpdateMessagesList(Integer second_contact_id)
{
this.removeAllItems();
RecordCount = 0;
setPageLength(0);
Integer current_contact_id = TempClass.current_user_id;
Connection Connection1 = null;

String SQLString =
"select mes.message_id, solid.pkg_user.f_get_full_fio (mes.to_user_id) to_fio "
+ ",mes.message_text ,mes.mesage_date "
+ ",decode (mes.from_user_id, ?, true,false) inc_msg"
+ ",su.user_photo_link from solid.message mes "
+ "join solid.system_users su on mes.from_user_id = su.user_id "
+ "where ((mes.from_user_id = ?) and (mes.to_user_id = ?) or (mes.from_user_id = ?) and (mes.to_user_id = ?)) "
+ "order by mes.message_id asc";

try
{
Class.forName(staticMethods.JDBC_DRIVER);
Connection1 = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
PreparedStatement PrepStm1  = Connection1.prepareCall(SQLString);

PrepStm1.setInt(1,current_contact_id);
PrepStm1.setInt(2,current_contact_id);
PrepStm1.setInt(3,second_contact_id);
PrepStm1.setInt(4,second_contact_id);
PrepStm1.setInt(5,current_contact_id);

ResultSet ResultSet1 = PrepStm1.executeQuery();
String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

String msg_from_fio;
String msg_text;
Integer msg_id;
String msg_date_text;

Boolean msg_incoming;
String photo_link;

SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

String SQLString2 = "select el.link_url, el.link_title from solid.external_link el "
+ "where el.message_id = ? order by el.external_link_id asc";

PreparedStatement PrepStm2  = Connection1.prepareCall(SQLString2);
Integer msg_link_count;

while (ResultSet1.next())
{
msg_id = ResultSet1.getInt(1);
PrepStm2.setInt(1,msg_id);
msg_link_count = 0;

ResultSet ResultSet2 = PrepStm2.executeQuery();
VerticalLayout LinkLayout = new VerticalLayout();

// Loop по ссылкам соообщения
String msg_link_url;
String msg_link_title;

while (ResultSet2.next())
{
msg_link_count = msg_link_count + 1;
msg_link_url = ResultSet2.getString(1);
msg_link_title = ResultSet2.getString(2);
Link ExtLink = new Link(msg_link_title,new ExternalResource(msg_link_url));
ExtLink.setTargetName("_blank");
LinkLayout.addComponent(ExtLink);
}

ResultSet2.close();

if (msg_link_count == 0)
{
LinkLayout = null;
}

msg_from_fio = ResultSet1.getString(2);
msg_text = ResultSet1.getString(3);
msg_date_text = DATE_FORMAT.format(ResultSet1.getTimestamp(4).getTime());
msg_incoming = ResultSet1.getBoolean(5);
photo_link =  basepath + TempClass.FolderSeparateCharacter + "VAADIN" + TempClass.FolderSeparateCharacter + "contactavatars" + TempClass.FolderSeparateCharacter + ResultSet1.getString(6);
FileResource resource = new FileResource(new File(photo_link));

MessageItem Mesg = new MessageItem(resource, msg_text, msg_from_fio, msg_date_text, LinkLayout, msg_incoming);
AddMessage(Mesg);
}
ResultSet1.close();
PrepStm1.close();
PrepStm2.close();

} //try for connection

catch (SQLException SQLe)
{
SQLe.printStackTrace();
}
catch (Exception e1)
{
e1.printStackTrace();
}

finally
{
if (Connection1 != null)

try
{
Connection1.close();
}
catch (Exception e2)
{
e2.printStackTrace();
}
}

} // UpdateMessagesList

}