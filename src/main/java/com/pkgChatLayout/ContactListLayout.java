package com.pkgChatLayout;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
* Created by Dmitriy on 24.06.2018.
*/

public class ContactListLayout extends VerticalLayout
{

public ContactListLayout()
{
setWidth("100%");

HorizontalLayout hlayout11 = new HorizontalLayout();
HorizontalLayout hlayout12 = new HorizontalLayout();
HorizontalLayout hlayout13 = new HorizontalLayout();

hlayout11.setWidth("100%");
hlayout12.addStyleName("hlayout-with-padding");
hlayout12.addStyleName("hlayout-with-borders");
hlayout12.setWidth("100%");
hlayout13.setWidth("100%");

/* hlayout11 */
/* hlayout11 */

/* hlayout12 */

ContactFilterTextField ContactFilterTextField1 = new ContactFilterTextField();
ContactFilterTextField1.setWidth("100%");
hlayout12.addComponent(ContactFilterTextField1);

/* hlayout12 */

/* hlayout13 */

ContactListTable ContactListTable1 = new ContactListTable();
ContactListTable1.setWidth("100%");
ContactListTable1.GetContactList();

ContactFilterTextField1.RelContactListTable = ContactListTable1;
hlayout13.addComponent(ContactListTable1);

/* hlayout13 */

addComponent(hlayout11);
addComponent(hlayout12);
addComponent(hlayout13);

}
}
