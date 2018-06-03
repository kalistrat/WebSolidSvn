package com.pkgChatLayout;

import com.staticMethods;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

/**
* Created by Dmitriy on 07.01.2018.
*/


public class ContactListTable extends Table
{

private Integer RecordCount ;

//Контейнер для хранения данных всех контактов
public IndexedContainer AllContactsContainer;

//Контейнер для хранения данных найденных контактов
public IndexedContainer SelectedContactsContainer;

//Связанный контейнер
IndexedContainer ActiveContainer;

//Таблица которая будет обновляться после клика в контакт-листе
MessageListTable RelMessageListTable;

public ContactListTable ()
{
AllContactsContainer = new IndexedContainer();
SelectedContactsContainer = new IndexedContainer();

//Номер записи п/п
AllContactsContainer.addContainerProperty("TableRecordNum", Integer.class,0);
SelectedContactsContainer.addContainerProperty("TableRecordNum", Integer.class,0);

//ФИО
AllContactsContainer.addContainerProperty("FIO", String.class,null);
SelectedContactsContainer.addContainerProperty("FIO", String.class,null);

//Путь к фотографии
AllContactsContainer.addContainerProperty("ContactPicturePath", String.class,null);
SelectedContactsContainer.addContainerProperty("ContactPicturePath", String.class,null);

// ID контакта
AllContactsContainer.addContainerProperty("SubjectId", Integer.class,0);
SelectedContactsContainer.addContainerProperty("SubjectId", Integer.class,0);

addStyleName("components-inside");
addContainerProperty("ContactListTableImageColumn",Image.class, null);
addContainerProperty("ContactListTableLabelColumn",Label.class, null);
setColumnWidth("ContactListTableImageColumn", 40);
setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
RecordCount = 0;
setWidth("100%");
setPageLength(1);

addStyleName(ValoTheme.TABLE_BORDERLESS) ;
addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;

// Allow selecting items from the table.
setSelectable(true);

// Send changes in selection immediately to server.
setImmediate(true);

ActiveContainer = AllContactsContainer;

GetFullContactListXML();

addValueChangeListener(new Property.ValueChangeListener()
{
@Override public void valueChange(Property.ValueChangeEvent valueChangeEvent)
{
Object SelectedRowObject = getValue();

if (SelectedRowObject != null)
{
//Номер выделенной строки таблицы
Integer IntRowNumber = Integer.valueOf(SelectedRowObject.toString());

// id субъекта для данной записи таблицы
Integer SubjectId = Integer.valueOf(ActiveContainer.getContainerProperty(IntRowNumber, "SubjectId").getValue().toString());

TempClass.second_user_id = SubjectId;

//Обновляем список сообщений
RelMessageListTable.UpdateMessagesList(SubjectId);
}
}
}
);

}

private String GetFullContactListXML()
{
try
{
Class.forName(staticMethods.JDBC_DRIVER);
Connection Con = DriverManager.getConnection(staticMethods.DB_URL, staticMethods.USER, staticMethods.PASS);
CallableStatement Stmt = Con.prepareCall("{? = call solid.pkg_contactlist.f_getfullcontactlistxml(?)}");
Stmt.registerOutParameter(1, Types.CLOB);
Stmt.setInt(2,TempClass.current_user_id);
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


public void GetContactList()
{
try
{
Document XMLDocument = staticMethods.loadXMLFromString(GetFullContactListXML());
NodeList nodes = XMLDocument.getElementsByTagName("contact");
for (int i = 0; i < nodes.getLength(); i++)
{

Element element = (Element) nodes.item(i);
Node node_user_id = element.getElementsByTagName("user_id").item(0);
Node node_fio = element.getElementsByTagName("fio").item(0);
Node node_user_photo_link = element.getElementsByTagName("user_photo_link").item(0);
AddContactItem(node_fio.getTextContent(), node_user_photo_link.getTextContent(), Integer.valueOf(node_user_id.getTextContent()));
}

}
catch (Exception ex)
{
ex.printStackTrace();
}

}

public Integer GetRecordCount()
{
return RecordCount;
}

public void AddContactItem ( ContactListItem NewContact)
{
Label LabelContactName = new Label(NewContact.ContactName);
Image ContactImage = new Image();
ContactImage.setWidth("30px");
ContactImage.setHeight("30px");
ContactImage.setSource(new ThemeResource(NewContact.ContactPicturePath));

RecordCount = RecordCount + 1;
addItem(new Object[]{ContactImage, LabelContactName}, RecordCount);
setPageLength(RecordCount);

Item newItem = ActiveContainer.addItem(RecordCount);
newItem.getItemProperty("TableRecordNum").setValue(RecordCount);
newItem.getItemProperty("FIO").setValue(NewContact.ContactName);
newItem.getItemProperty("ContactPicturePath").setValue(NewContact.ContactPicturePath);
newItem.getItemProperty("SubjectId").setValue(NewContact.SubjectId);
}

public void AddContactItem (String vContactFIO, String vContactPicturePath,Integer vSubjectId)
{
Label LabelContactName = new Label(vContactFIO);
Image ContactImage = new Image();
ContactImage.setWidth("30px");
ContactImage.setHeight("30px");
ContactImage.setSource(new ThemeResource(vContactPicturePath));

RecordCount = RecordCount + 1;
addItem(new Object[]{ContactImage, LabelContactName}, RecordCount);
setPageLength(RecordCount);

Item newItem = ActiveContainer.addItem(RecordCount);
newItem.getItemProperty("TableRecordNum").setValue(RecordCount);
newItem.getItemProperty("FIO").setValue(vContactFIO);
newItem.getItemProperty("ContactPicturePath").setValue(vContactPicturePath);
newItem.getItemProperty("SubjectId").setValue(vSubjectId);
}

public void NullifyRecordCount()
{
RecordCount = 0;
}

}