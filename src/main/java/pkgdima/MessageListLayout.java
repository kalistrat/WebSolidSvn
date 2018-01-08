package pkgdima;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


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
hlayout11.addStyleName("blue-layout");
hlayout12.setWidth("100%");
hlayout13.setWidth("100%");

hlayout21.setWidth("100%");
hlayout21.addStyleName("blue-layout");
hlayout22.setWidth("100%");
hlayout23.setWidth("100%");


vlayout1.addComponent(hlayout11);
vlayout1.addComponent(hlayout12);
vlayout1.addComponent(hlayout13);

vlayout2.addComponent(hlayout21);
vlayout2.addComponent(hlayout22);
vlayout2.addComponent(hlayout23);

/* TableForContactFilterTextField */
Table TableForContactFilterTextField = new Table();
TableForContactFilterTextField.setWidth("100%");
TableForContactFilterTextField.addContainerProperty("column1",TextField.class, null);
TextField ContactFilterTextField = new TextField("");
ContactFilterTextField.setWidth("100%");
TableForContactFilterTextField.addItem(new Object[]{ ContactFilterTextField},1);
TableForContactFilterTextField.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
TableForContactFilterTextField.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
hlayout12.addComponent(TableForContactFilterTextField);
TableForContactFilterTextField.setPageLength(1);


/* TableForContactFilterTextField */


/* ContactListTable */
ContactListTable ContactListTable1 = new ContactListTable();
ContactListTable1.setWidth("100%");

Image NewContactPicture = new Image();
NewContactPicture.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
NewContactPicture.setWidth(30, Unit.PIXELS);
NewContactPicture.setHeight(30, Unit.PIXELS);

ContactListItem NewContact = new ContactListItem (NewContactPicture, "Contact Name");
ContactListTable1.AddContactItem (NewContact);

Image NewContactPicture1 = new Image();
NewContactPicture1.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
NewContactPicture1.setWidth(30, Unit.PIXELS);
NewContactPicture1.setHeight(30, Unit.PIXELS);
ContactListItem NewContact1 = new ContactListItem (NewContactPicture1, "Contact Name1");

ContactListTable1.AddContactItem (NewContact1);
//ContactListTable1.setPageLength(ContactListTable1.GetRecordCount());
hlayout13.addComponent(ContactListTable1);
/* ContactListTable */

/* MessageListTable */
Image NewContactImage = new Image();
NewContactImage.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));

MessageListTable MsgListTable1 = new MessageListTable();
MessageListItem NewMessage = new MessageListItem(NewContactImage,"Text","Name","Date");
MsgListTable1.AddMessage(NewMessage);
hlayout22.addComponent(MsgListTable1);

/* MessageListTable */

/*  MessageTextArea */
TextArea MessageTextArea = new TextArea();
// MessageTextArea.setWidth("100%");
MessageTextArea.setRows(1);
MessageTextArea.setSizeFull();
hlayout23.addComponent(MessageTextArea);
/*  MessageTextArea */

this.addComponent(HrSplitPanel);
}
}

