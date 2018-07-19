package com.pkgChatLayout;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.TextField;

// http://www.sql.ru/forum/1123094/vaadin-obrabotka-nazhatiya-enter-v-textfield

public class ContactFilterTextField extends TextField
{

//ContactListTable которую будем обновлять
public ContactListTable RelContactListTable;

//Фильтр по контактам
public void FilterContactlist()
{
String SearchFIO = getValue().toLowerCase();
String FIO, Photopath;
Integer SubjectId;

RelContactListTable.removeAllItems();
RelContactListTable.SelectedContactsContainer.removeAllItems();
RelContactListTable.NullifyRecordCount();
RelContactListTable.ActiveContainer = RelContactListTable.SelectedContactsContainer;

for (Integer i =1; i<= RelContactListTable.AllContactsContainer.size(); i=i+1)
{
FIO = RelContactListTable.AllContactsContainer.getContainerProperty(i, "FIO").getValue().toString();
Photopath = RelContactListTable.AllContactsContainer.getContainerProperty(i, "ContactPicturePath").getValue().toString();
SubjectId = Integer.valueOf(RelContactListTable.AllContactsContainer.getContainerProperty(i, "SubjectId").getValue().toString());

if (FIO.toLowerCase().contains(SearchFIO) )
{
RelContactListTable.AddContactItem(FIO,Photopath,SubjectId);
}
}
}

public ContactFilterTextField ()
{
setImmediate(true);
setInputPrompt("Поиск...");

addShortcutListener(new ShortcutListener("Execute", ShortcutAction.KeyCode.ENTER, null)
{
@Override public void handleAction(Object sender, Object target)
{
FilterContactlist();
}
});
}
}