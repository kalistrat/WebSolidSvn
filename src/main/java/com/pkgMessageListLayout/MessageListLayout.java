package com.pkgMessageListLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;


/**
* Created by Dmitriy on 05.01.2018.
*/

@Theme("mytheme")
public class MessageListLayout extends VerticalLayout
{

public MessageListLayout()
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

SmartTextField ContactFilterTextField = new SmartTextField();
ContactFilterTextField.setWidth("100%");
ContactFilterTextField.setValue("Поиск...");
hlayout12.addComponent(ContactFilterTextField);

/* hlayout12 */

/* hlayout13 */

ContactListTable ContactListTable1 = new ContactListTable();
ContactListTable1.setWidth("100%");

Image NewContactPicture1 = new Image();
NewContactPicture1.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
ContactListItem NewContact1 = new ContactListItem (NewContactPicture1, "Contact Name");
NewContact1.setWidth("100%");
ContactListTable1.AddContactItem (NewContact1);


Image NewContactPicture2 = new Image();
NewContactPicture2.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
ContactListItem NewContact2 = new ContactListItem (NewContactPicture2, "Contact1 Name1");
NewContact2.setWidth("100%");
ContactListTable1.AddContactItem (NewContact2);
hlayout13.addComponent(ContactListTable1);

/* hlayout13 */


/* hlayout21 */
/* hlayout21 */

/* hlayout22 */
VerticalLayout VLinkLayout = new VerticalLayout();
VLinkLayout.setWidth("100%");

Link link = new Link("Take me a away to a faraway land",         new ExternalResource("http://vaadin.com/"));
link.setTargetName("_blank");

VLinkLayout.addComponent(link);
Image NewContactImage = new Image();
NewContactImage.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
MessageListTable MsgListTable1 = new MessageListTable();
MessageListItem NewMessage = new MessageListItem(NewContactImage,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.","Ivanov Ivan Sergeevich","11.01.2017 23:59:59", VLinkLayout,1);
MsgListTable1.AddMessage(NewMessage);
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

Table MessageTextAreaTable = new Table ();
MessageTextAreaTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableTextAreaColumn", TextArea.class, null);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableChooseFilesButton", Button.class, null);
MessageTextAreaTable.addContainerProperty("MessageTextAreaTableSendMessageButton", Button.class, null);

MessageTextAreaTable.setColumnWidth("MessageTextAreaTableTextAreaColumn", 700);
MessageTextAreaTable.setColumnWidth("MessageTextAreaTableChooseFilesButton", 50);
MessageTextAreaTable.setColumnWidth("MessageTextAreaTableSendMessageButton", 150);

MessageTextAreaTable.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
MessageTextAreaTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
MessageTextAreaTable.setWidth("100%");
MessageTextAreaTable.addItem(new Object[]{MessageTextArea, ChooseFilesButton, SendMessageButton},1);
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

