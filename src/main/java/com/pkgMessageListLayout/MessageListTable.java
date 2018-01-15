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
t1.addContainerProperty("MessageListTableColumn1",MessageListItem3.class, null);
RecordCount = 0;
setContainerDataSource(t1);
setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);

addStyleName(ValoTheme.TABLE_BORDERLESS) ;
//addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
//setSizeFull();
setWidth("100%");
}

public  Integer GetRecordCount()
{
return RecordCount;
}

/*
public void AddMessage (MessageListItem NewMessage)
{
addItem(new Object[]{NewMessage}, RecordCount + 1);
RecordCount = RecordCount + 1;
setPageLength(RecordCount);
}
*/

public void AddMessage2 (MessageListItem2 NewMessage)
{
addItem(new Object[]{NewMessage}, RecordCount + 1);
RecordCount = RecordCount + 1;
setPageLength(RecordCount);
}

public void AddMessage3 (MessageListItem3 NewMessage)
{
addItem(new Object[]{NewMessage}, RecordCount + 1);
RecordCount = RecordCount + 1;
setPageLength(RecordCount);
}
}
