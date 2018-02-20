package com.pkgMessageListLayout;

import com.vaadin.ui.*;

/**
 * Created by Dmitriy on 06.01.2018.
 */

public  class ContactListItem
{
Image ContactPicture;
String ContactName;

public ContactListItem( Image vContactPicture, String vContactName )
{
this.ContactPicture = vContactPicture;
this.ContactName = vContactName;
}

};