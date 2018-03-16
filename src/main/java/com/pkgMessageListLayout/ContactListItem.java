package com.pkgMessageListLayout;

import com.vaadin.ui.Image;

/**
 * Created by Dmitriy on 06.01.2018.
 */

public  class ContactListItem
{
Image ContactPicture;
String ContactName;

public ContactListItem( Image vContactPicture, String vContactName )
{
ContactPicture = vContactPicture;
ContactName = vContactName;
}

};