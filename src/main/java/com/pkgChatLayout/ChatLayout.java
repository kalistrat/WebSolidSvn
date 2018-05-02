package com.pkgChatLayout;

import com.staticMethods;
import com.vaadin.annotations.Theme;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;

import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
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

TextField ContactFilterTextField = new TextField();
ContactFilterTextField.setWidth("100%");
ContactFilterTextField.setInputPrompt("Поиск...");
hlayout12.addComponent(ContactFilterTextField);

/* hlayout12 */

/* hlayout13 */

ContactListTable ContactListTable1 = new ContactListTable();
ContactListTable1.setWidth("100%");

String SQLString = "select su.user_id,su.second_name , su.first_name , su.middle_name, su.user_photo_link" +
" from solid.system_users su where su.user_id!=" + TempClass.current_user_id.toString() + " order by su.user_id asc";

Connection Connection1 = null;

try
{
Class.forName(staticMethods.JDBC_DRIVER);
Connection1 = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);

Statement Statement = Connection1.createStatement();
ResultSet ResultSet1 = Statement.executeQuery(SQLString);

Integer rec_user_id;
String rec_fio;

while (ResultSet1.next())
{
rec_user_id = ResultSet1.getInt(1);
rec_fio = ResultSet1.getString(2) + " " + ResultSet1.getString(3) + " " + ResultSet1.getString(4);
ContactListItem NewContact2 = new ContactListItem(ResultSet1.getString(5), rec_fio, rec_user_id);
ContactListTable1.AddContactItem(NewContact2);
}
ResultSet1.close();
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

hlayout13.addComponent(ContactListTable1);

/* hlayout13 */


/* hlayout21 */
/* hlayout21 */

/* hlayout22 */

VerticalLayout VLinkLayout = new VerticalLayout();
VLinkLayout.setWidth("100%");

Link link = new Link("Take me a away to a faraway land", new ExternalResource("http://vaadin.com/"));
link.setTargetName("_blank");

VLinkLayout.addComponent(link);
MessageListTable MsgListTable1 = new MessageListTable();
ContactListTable1.SetMessageListTable(MsgListTable1);
hlayout22.addComponent(MsgListTable1);

/* hlayout22 */

/* hlayout23 */
Button ChooseFilesButton = new Button("");
Button SendMessageButton = new Button("Отправить");

String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

// Image as a file resource
FileResource resource = new FileResource(new File(basepath + "/VAADIN/themes/mytheme/paperclip.ico"));

ChooseFilesButton.setIcon(resource);
ChooseFilesButton.setWidth("40px");
SendMessageButton.setWidth("110px");
ChooseFilesButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);

TextArea MessageTextArea = new TextArea();
MessageTextArea.setWidth("100%");
MessageTextArea.setRows(1);
//MessageTextArea.setSizeFull();

Table MessageTextAreaTable = new Table();
MessageTextAreaTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableTextAreaColumn", TextArea.class, null);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableChooseFilesButton", Button.class, null);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableSendMessageButton", Button.class, null);

MessageTextAreaTable.setColumnWidth("MessageTextAreaTableTextAreaColumn", 700);
MessageTextAreaTable.setColumnWidth("MessageTextAreaTableChooseFilesButton", 50);
MessageTextAreaTable.setColumnWidth("MessageTextAreaTableSendMessageButton", 150);

MessageTextAreaTable.addStyleName(ValoTheme.TABLE_BORDERLESS);
MessageTextAreaTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
MessageTextAreaTable.setWidth("100%");
MessageTextAreaTable.addItem(new Object[]{MessageTextArea, ChooseFilesButton, SendMessageButton}, 1);
MessageTextAreaTable.setPageLength(1);
hlayout23.addComponent(MessageTextAreaTable);

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
}
