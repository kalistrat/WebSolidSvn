package com.pkgMessageListLayout;

import com.vaadin.server.Resource;

/**
 * Created by Dmitriy on 06.01.2018.
 */

public class ContactListItem
{
Resource ContactPicture;
String ContactName;
Integer SubjectId;

public ContactListItem( Resource vContactPicture, String vContactName, Integer vSubjectId )
{
ContactPicture = vContactPicture;
ContactName = vContactName;
SubjectId = vSubjectId;
}

};