package com.pkgMessageListLayout;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
* Created by Dmitriy on 07.01.2018.
*/

public class ContactListTable extends Table
{

private Integer RecordCount ;
IndexedContainer DataContainer;

public ContactListTable ()
{
DataContainer = new IndexedContainer();

DataContainer.addContainerProperty("RecordNum", Integer.class,0);
DataContainer.addContainerProperty("ContactId", Integer.class,0);

addStyleName("components-inside");
addContainerProperty("ContactListTableImageColumn",Image.class, null);
addContainerProperty("ContactListTableLabelColumn",Label.class, null);
setColumnWidth("ContactListTableImageColumn", 40);
setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
RecordCount = 0;

addStyleName(ValoTheme.TABLE_BORDERLESS) ;
addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;

setWidth("100%");
setPageLength(1);

// Allow selecting items from the table.
setSelectable(true);

// Send changes in selection immediately to server.
setImmediate(true);


addValueChangeListener(new Property.ValueChangeListener()
{
@Override public void valueChange(Property.ValueChangeEvent valueChangeEvent)
{
Object SelectedRowObject = getValue();

if (SelectedRowObject != null)
{

//Номер выделенной строки таблицы
Integer IntRowNumber = Integer.valueOf(SelectedRowObject.toString());
Object Obj = DataContainer.getIdByIndex(IntRowNumber - 1);
Integer SubjectId = Integer.valueOf(DataContainer.getContainerProperty(Obj, "ContactId").getValue().toString());
Notification.show("SubjectId = " + SubjectId);
}

}
});

}

public Integer GetRecordCount()
{
return RecordCount;
}

public void AddContactItem ( ContactListItem NewContact)
{
RecordCount = RecordCount + 1;

Label LabelContactName = new Label(NewContact.ContactName);
Image ContactImage = new Image();

ContactImage.setWidth("30px");
ContactImage.setHeight("30px");
ContactImage.setSource(NewContact.ContactPicture);

addItem(new Object[]{ContactImage, LabelContactName}, RecordCount);
setPageLength(RecordCount);

Item newItem = DataContainer.addItem(RecordCount);
newItem.getItemProperty("RecordNum").setValue(RecordCount);
newItem.getItemProperty("ContactId").setValue(NewContact.SubjectId);

}
}