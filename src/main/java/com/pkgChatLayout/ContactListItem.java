package com.pkgChatLayout;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;

/**
* Created by Dmitriy on 06.01.2018.
*/

public class ContactListItem
{
String ContactPicturePath;
String ContactName;
Integer SubjectId;

public ContactListItem( String vContactPicturePath, String vContactName, Integer vSubjectId )
{
ContactPicturePath = vContactPicturePath;
ContactName = vContactName;
SubjectId = vSubjectId;
}

    /*
public ContactListItem( Resource vContactPicture, String vContactName, Integer vSubjectId )
{
ContactPicture = vContactPicture;
ContactName = vContactName;
SubjectId = vSubjectId;
}

public ContactListItem( FileResource FRContactPicture, String vContactName, Integer vSubjectId )
{
ContactPicture = FRContactPicture;
ContactName = vContactName;
SubjectId = vSubjectId;
}
*/
}
;