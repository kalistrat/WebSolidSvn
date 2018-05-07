package com.pkgChatLayout;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.TextField;

// http://www.sql.ru/forum/1123094/vaadin-obrabotka-nazhatiya-enter-v-textfield

public class ContactFilterTextField extends TextField
{

//ContactListTable который будем обновлять
public ContactListTable RelContactListTable;

public  void FilterContactlist()
{
String SearchFIO = getValue();
String FIO,Photopath;
Integer SubjectId;

RelContactListTable.removeAllItems();
RelContactListTable.NullifyRecordCount();

RelContactListTable.SelectedContactsContainer.removeAllItems();
RelContactListTable.ActiveContainer = RelContactListTable.SelectedContactsContainer;

for (Integer i =0; i<= RelContactListTable.AllContactsContainer.size()-1; i=i+1)
{
//Объект таблицы
Object Obj = RelContactListTable.AllContactsContainer.getIdByIndex(i);
FIO = RelContactListTable.AllContactsContainer.getContainerProperty(Obj, "FIO").getValue().toString();
Photopath = RelContactListTable.AllContactsContainer.getContainerProperty(Obj, "ContactPicturePath").getValue().toString();
SubjectId = Integer.valueOf(RelContactListTable.AllContactsContainer.getContainerProperty(Obj, "SubjectId").getValue().toString());

if (FIO.indexOf(SearchFIO) > -1)
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