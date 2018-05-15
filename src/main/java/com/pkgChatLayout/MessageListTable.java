package com.pkgChatLayout;

import com.staticMethods;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;

import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;
import java.text.SimpleDateFormat;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
RecordCount = RecordCount + 1;
addItem(new Object[]{NewMessage}, RecordCount);
setPageLength(RecordCount);
}
private String GetMessagesListString (Integer second_contact_id)
{
try
{
Class.forName(staticMethods.JDBC_DRIVER);
Connection Con = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
CallableStatement Stmt = Con.prepareCall("{? = call solid.pkg_contactlist.f_get_messagelistclob(?,?)}");
Stmt.registerOutParameter(1, Types.CLOB);
Stmt.setInt(2,TempClass.current_user_id);
Stmt.setInt(3,second_contact_id);
Stmt.execute();
String resultStr = staticMethods.clobToString(Stmt.getClob(1));
Con.close();
return resultStr;
}
catch(SQLException se)
{
se.printStackTrace();
return null;
}
catch(Exception e)
{
e.printStackTrace();
return null;
}

};
//Обновить список сообщений для указанного id собеседника
public void UpdateMessagesList2 (Integer second_contact_id)
{
removeAllItems();
RecordCount = 0;
setPageLength(0);
Integer current_contact_id = TempClass.current_user_id;

try
{
Document XMLDocument = staticMethods.loadXMLFromString(GetMessagesListString(second_contact_id));
NodeList nodes = XMLDocument.getElementsByTagName("contact");


for (int i = 0; i < nodes.getLength(); i++)
{
Element element = (Element) nodes.item(i);
Node node_from_user_fio= element.getElementsByTagName("from_user_fio").item(0);
Node node_message_text= element.getElementsByTagName("message_text").item(0);
Node node_message_date= element.getElementsByTagName("message_date").item(0);
Node node_inc_msg= element.getElementsByTagName("inc_msg").item(0);
Node node_user_photo_link= element.getElementsByTagName("user_photo_link").item(0);
MessageItem NewMessage = new MessageItem(node_user_photo_link.getTextContent() ,node_message_text.getTextContent() ,node_from_user_fio.getTextContent() ,node_message_date.getTextContent(), null , (Integer.valueOf(node_inc_msg.getTextContent()) == 1 ));
AddMessage(NewMessage);
}


}
catch (Exception ex)
{
ex.printStackTrace();
}


}

/*
public void UpdateMessagesList(Integer second_contact_id)
{
Connection Connection1 = null;

String SQLString =
"select mes.message_id, solid.pkg_user.f_get_full_fio (mes.from_user_id) to_fio "
+ ",mes.message_text ,mes.message_date "
+ ",decode (mes.from_user_id, ?, 1,0) inc_msg"
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

String msg_from_fio;
String msg_text;
Integer msg_id;
String msg_date_text;
Boolean msg_incoming;

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

if (ResultSet1.getInt(5) == 1)
{
msg_incoming = true;
}
else
{
msg_incoming = false;
}

ThemeResource ThemeResource1 = new ThemeResource( ResultSet1.getString(6));
MessageItem Mesg = new MessageItem(ThemeResource1, msg_text, msg_from_fio, msg_date_text, LinkLayout, msg_incoming);
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
*/
}