package com.pkgChatLayout;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.TextField;

public class ContactFilterTextField extends TextField
{
//ContactListTable который будем обновлять
public ContactListTable RelContactListTable;

public ContactFilterTextField ()
{
setImmediate(true);
setInputPrompt("Поиск...");


// http://www.sql.ru/forum/1123094/vaadin-obrabotka-nazhatiya-enter-v-textfield
addShortcutListener(new ShortcutListener("Execute", ShortcutAction.KeyCode.ENTER, null)
{
@Override public void handleAction(Object sender, Object target)
{
RelContactListTable.removeAllItems();
RelContactListTable.SelectedContactsContainer.removeAllItems();

String FIO;
String Photopath;
Integer SubjectId;

for (Integer i =0; i<= RelContactListTable.GetRecordCount() - 1; i=i+1)
{
//Объект таблицы
Object Obj = RelContactListTable.AllContactsContainer.getIdByIndex(i);
FIO = RelContactListTable.AllContactsContainer.getContainerProperty(Obj, "FIO").getValue().toString();
Photopath = RelContactListTable.AllContactsContainer.getContainerProperty(Obj, "ContactPicturePath").getValue().toString();
SubjectId = Integer.valueOf(RelContactListTable.AllContactsContainer.getContainerProperty(Obj, "SubjectId").getValue().toString());

if (FIO.indexOf(SearchString) > 0)
{
RelContactListTable.SelectedContactsContainer.addItem(Obj);
RelContactListTable.AddContactItem(FIO,Photopath,SubjectId);
}

}
RelContactListTable.ActiveContainer = RelContactListTable.SelectedContactsContainer;
}
});
}
}
