package com.pkgMessageListLayout;

import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;


/**
* Created by Dmitriy on 07.01.2018.
*/

public class MessageListTable extends Table
{
private Integer RecordCount ;

public MessageListTable ()
{
Table t1 = new Table("");
t1.addContainerProperty("MessageListTableColumn1",MessageListItem.class, null);
this.RecordCount = 0;
this.setContainerDataSource(t1);
this.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);

this.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
//this.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
this.addStyleName("components-inside");
//this.setSizeFull();
this.setWidth("100%");
}

public  Integer GetRecordCount()
{
return this.RecordCount;
}

public void AddMessage (MessageListItem NewMessage)
{
this.addItem(new Object[]{NewMessage}, this.RecordCount + 1);
this.RecordCount = this.RecordCount + 1;
this.setPageLength(this.RecordCount);
}
}
