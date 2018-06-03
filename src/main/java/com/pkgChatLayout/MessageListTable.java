package com.pkgChatLayout;

import com.staticMethods;
import com.vaadin.server.ExternalResource;

import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
* Created by Dmitriy on 07.01.2018.

// http://qaru.site/questions/281307/java-elementgetelementsbytagname-restrict-to-top-level

Чтобы брать ссылки для текущего сообщения, так как
XMLDocument.getElementsByTagName("links") возвращает все ссылки
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
CallableStatement Stmt = Con.prepareCall("{? = call solid.pkg_messagelist.f_getmessagelistxml(?,?)}");
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
}

//Обновить список сообщений для указанного id собеседника
public void UpdateMessagesList (Integer second_contact_id)
{
removeAllItems();
RecordCount = 0;
setPageLength(0);

try
{
Document XMLDocument = staticMethods.loadXMLFromString(GetMessagesListString(second_contact_id));

//Список узлов <message>
NodeList NodeListMessages = XMLDocument.getElementsByTagName("message");

String message_id;

//Цикл по узлам <message>
for (int i = 0; i < NodeListMessages.getLength(); i++)
{
Element Element1 = (Element) NodeListMessages.item(i);
message_id = Element1.getElementsByTagName("message_id").item(0).getTextContent();
Node node_second_name= Element1.getElementsByTagName("second_name").item(0);
Node node_first_name= Element1.getElementsByTagName("first_name").item(0);
Node node_middle_name= Element1.getElementsByTagName("middle_name").item(0);
Node node_message_text= Element1.getElementsByTagName("message_text").item(0);
Node node_message_date= Element1.getElementsByTagName("message_date").item(0);
Node node_to_user_id= Element1.getElementsByTagName("to_user_id").item(0);
Node node_user_photo_link= Element1.getElementsByTagName("user_photo_link").item(0);
String StrFIO = node_second_name.getTextContent() + " " + node_first_name.getTextContent() + " " + node_middle_name.getTextContent();
String Strpath = "/messages/message[message_id = "+ "'" +   message_id +  "'" +"]/links";

//External links
XPathFactory xpathFactory = XPathFactory.newInstance();
XPath xpath = xpathFactory.newXPath();

/* Узел <links> </links> для текущего сообщения */
NodeList NodeListLinks = (NodeList) xpath.evaluate(Strpath, XMLDocument,   XPathConstants.NODESET);
Integer msg_link_count = 0;

VerticalLayout LinkLayout = new VerticalLayout();

//Список <LINK_URL> <LINK_TITLE> текущего сообщения
NodeList NodeLinksData = NodeListLinks.item(0).getChildNodes();

String link_url="" ;
String link_title="" ;

// Проходим по списку элементов
for (int k = 0; k < NodeLinksData.getLength(); k++)
{

//Если текущий элемент - <LINK_URL>, то сохраняем значение элемента в link_url
if (NodeLinksData.item(k).getNodeName().equals("LINK_URL"))
{
link_url = NodeLinksData.item(k).getTextContent();
}

//Если текущий элемент - <LINK_TITLE>, то сохраняем значение элемента в link_title
if (NodeLinksData.item(k).getNodeName().equals("LINK_TITLE"))
{
link_title = NodeLinksData.item(k).getTextContent();
}

//На четных шагах прохода по циклу анализируем link_url и  link_title
//К данному шагу они уже определены
if ((k+1)%2 == 0)
{

//Если есть URL и описание ссылки - добавляем ссылку в список
if ((!link_url.equals( "")) && (!link_title.equals("") ))
{
msg_link_count = msg_link_count + 1;
Link ExtLink = new Link(link_title, new ExternalResource(link_url));
ExtLink.setTargetName("_blank");
LinkLayout.addComponent(ExtLink);
}
}
}
//External links

if (msg_link_count == 0)
{
LinkLayout = null;
}

MessageItem NewMessage = new MessageItem(node_user_photo_link.getTextContent() ,node_message_text.getTextContent() ,StrFIO ,node_message_date.getTextContent(), LinkLayout , (Integer.valueOf(node_to_user_id.getTextContent()).equals( TempClass.current_user_id )));
AddMessage(NewMessage);
}

}
catch (Exception ex)
{
ex.printStackTrace();
}
}

}