package com.pkgMessageListLayout;

import com.staticMethods;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Table;
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
this.clear();

Integer current_contact_id = UserClass.current_user_id;
Connection Connection1 = null;

String SQLString =
"select solid.pkg_user.f_get_full_fio (mes.to_user_id) to_fio "
+ ",mes.message_text ,mes.mesage_date "
+ ",decode (mes.to_user_id, ?, 1,0) inc_msg"
+ ",su.user_photo_link from solid.message mes "
+ "join solid.system_users su on mes.from_user_id = su.user_id "
+ "where ((mes.from_user_id = ?) and (mes.to_user_id = ?) or (mes.from_user_id = ?) and (mes.to_user_id = ?)) "
+ "order by mes.message_id asc";

try
{
Class.forName(staticMethods.JDBC_DRIVER);
Connection1 = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
PreparedStatement PrepStm  = Connection1.prepareCall(SQLString);

PrepStm.setInt(1,current_contact_id);
PrepStm.setInt(2,current_contact_id);
PrepStm.setInt(3,second_contact_id);
PrepStm.setInt(4,second_contact_id);
PrepStm.setInt(5,current_contact_id);
ResultSet ResultSet1 = PrepStm.executeQuery();

String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

String from_fio;
String msg_text;
Integer inc_mes_int;
String msg_date_text;

Boolean inc_mes;
String photo_link;
SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
while (ResultSet1.next())
{
from_fio = ResultSet1.getString(1);
msg_text = ResultSet1.getString(2);
msg_date_text = DATE_FORMAT.format(ResultSet1.getDate(3));
inc_mes_int = ResultSet1.getInt(4);
if (inc_mes_int ==1) {inc_mes = true;} else {inc_mes =false;}
photo_link =  basepath + "/VAADIN/contactavatars/" + ResultSet1.getString(5);
FileResource resource = new FileResource(new File(photo_link));
MessageItem Mesg = new MessageItem(resource,msg_text,from_fio,msg_date_text,null,inc_mes);
AddMessage(Mesg);
}
}

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


















}

}
