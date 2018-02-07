package com.pkgMessageListLayout;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
* Created by Dmitriy on 07.01.2018.
*/

public class ContactListTable extends Table
{
private Integer RecordCount ;

public ContactListTable ()
{

addContainerProperty("ContactListTableColumn",ContactListItem.class, null);
RecordCount = 0;
setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);

addStyleName(ValoTheme.TABLE_BORDERLESS) ;
addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
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
System.out.println(getValue().toString());
}
});

}

public Integer GetRecordCount()
{
return RecordCount;
}

public void AddContactItem ( ContactListItem NewContact)
{
addItem(new Object[]{NewContact}, RecordCount + 1);
RecordCount = RecordCount + 1;
setPageLength(RecordCount);
}
}
