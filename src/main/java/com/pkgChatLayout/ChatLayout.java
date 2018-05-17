package com.pkgChatLayout;

import com.staticMethods;
import com.vaadin.annotations.Theme;

import com.vaadin.server.ThemeResource;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
* Created by Dmitriy on 05.01.2018.
*/

@Theme("mytheme")
public class ChatLayout extends VerticalLayout
{

public ChatLayout()
{
VerticalLayout vlayout1 = new VerticalLayout();
VerticalLayout vlayout2 = new VerticalLayout();

vlayout1.setWidth("100%");
vlayout2.setWidth("100%");

HorizontalSplitPanel HrSplitPanel = new HorizontalSplitPanel();
HrSplitPanel.addComponent(vlayout1);
HrSplitPanel.addComponent(vlayout2);
HrSplitPanel.setSplitPosition(20, Unit.PERCENTAGE);
//HrSplitPanel.setLocked(true);

HorizontalLayout hlayout11 = new HorizontalLayout();
HorizontalLayout hlayout12 = new HorizontalLayout();
HorizontalLayout hlayout13 = new HorizontalLayout();
HorizontalLayout hlayout21 = new HorizontalLayout();
HorizontalLayout hlayout22 = new HorizontalLayout();
HorizontalLayout hlayout23 = new HorizontalLayout();

hlayout11.setWidth("100%");
hlayout12.addStyleName("hlayout-with-padding");
hlayout12.addStyleName("hlayout-with-borders");
hlayout12.setWidth("100%");
hlayout13.setWidth("100%");

hlayout21.setWidth("100%");
hlayout22.setWidth("100%");
hlayout23.setWidth("100%");
hlayout23.addStyleName("hlayout-with-padding");
hlayout23.addStyleName("hlayout-with-borders");

/* hlayout11 */
/* hlayout11 */

/* hlayout12 */

ContactFilterTextField ContactFilterTextField1 = new ContactFilterTextField();
ContactFilterTextField1.setWidth("100%");
hlayout12.addComponent(ContactFilterTextField1);

/* hlayout12 */

/* hlayout13 */

ContactListTable ContactListTable1 = new ContactListTable();
ContactListTable1.setWidth("100%");
ContactListTable1.GetContactList();

ContactFilterTextField1.RelContactListTable = ContactListTable1;
hlayout13.addComponent(ContactListTable1);

/* hlayout13 */


/* hlayout21 */
/* hlayout21 */

/* hlayout22 */

MessageListTable MsgListTable1 = new MessageListTable();
ContactListTable1.RelMessageListTable = MsgListTable1;
hlayout22.addComponent(MsgListTable1);

/* hlayout22 */

/* hlayout23 */
Button ChooseFilesButton = new Button("");
Button SendMessageButton = new Button("Отправить");

ThemeResource ThemeResource1 = new ThemeResource("paperclip.ico");

// Image as a file resource
ChooseFilesButton.setIcon(ThemeResource1);
ChooseFilesButton.setWidth("40px");
SendMessageButton.setWidth("110px");
ChooseFilesButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);

TextArea MessageTextArea = new TextArea();
MessageTextArea.setWidth("100%");
MessageTextArea.setRows(1);
//MessageTextArea.setSizeFull();

Table MessageTextAreaTable = new Table();

MessageTextAreaTable.addStyleName("components-inside");
MessageTextAreaTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableTextAreaColumn", TextArea.class, null);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableSendMessageButton", Button.class, null);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableChooseFilesButton", Button.class, null);

MessageTextAreaTable.setColumnWidth("MessageTextAreaTableTextAreaColumn", 750);
MessageTextAreaTable.setColumnWidth("MessageTextAreaTableSendMessageButton", 150);
MessageTextAreaTable.setColumnWidth("MessageTextAreaTableChooseFilesButton", 80);

MessageTextAreaTable.addStyleName(ValoTheme.TABLE_BORDERLESS);
MessageTextAreaTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
MessageTextAreaTable.setWidth("100%");
MessageTextAreaTable.addItem(new Object[]{MessageTextArea, SendMessageButton, ChooseFilesButton}, 1);
MessageTextAreaTable.setPageLength(1);
hlayout23.addComponent(MessageTextAreaTable);


SendMessageButton.addClickListener(new Button.ClickListener()
{
@Override public void buttonClick(Button.ClickEvent clickEvent)
{
Integer v_from_user_id = TempClass.current_user_id;
Integer v_to_user_id = TempClass.second_user_id;
String v_message_text = MessageTextArea.getValue();
Connection con;

try
{
Class.forName(staticMethods.JDBC_DRIVER);
con = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
PreparedStatement PrepStm1  = con.prepareCall("call solid.pkg_messagelist.f_addmessage(?,?,?)");
PrepStm1.setInt(1,v_from_user_id);
PrepStm1.setInt(2,v_to_user_id);
PrepStm1.setString(3,v_message_text);
PrepStm1.execute();
con.close();
}
catch (Exception exp)
{
exp.printStackTrace();
}
MsgListTable1.UpdateMessagesList2(TempClass.second_user_id);
MessageTextArea.clear();;
}
});

/* hlayout23 */

vlayout1.addComponent(hlayout11);
vlayout1.addComponent(hlayout12);
vlayout1.addComponent(hlayout13);

vlayout2.addComponent(hlayout21);
vlayout2.addComponent(hlayout22);
vlayout2.addComponent(hlayout23);

this.addComponent(HrSplitPanel);
}


}
