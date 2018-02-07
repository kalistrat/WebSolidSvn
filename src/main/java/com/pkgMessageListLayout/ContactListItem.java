package com.pkgMessageListLayout;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dmitriy on 06.01.2018.
 */

public class ContactListItem extends Table
{
Image ContactPicture;
String ContactName;
/*
public ContactListItem()
{

}
*/

public ContactListItem( Image NewContactPicture, String NewContactName )
{
addStyleName("components-inside");
Image vNewImage = new Image();

if (NewContactPicture!= null)
{
vNewImage.setSource(NewContactPicture.getSource());
vNewImage.setWidth(30, Unit.PIXELS);
vNewImage.setHeight(40, Unit.PIXELS);
}
else
{
vNewImage=null;
}

Label LabelContactName = new Label (NewContactName) ;

addContainerProperty("ContactClassTablePictureColumn", Image.class, null);
addContainerProperty("ContactClassTableNameColumn", Label.class, null);
//ContactClassTable.setColumnExpandRatio("ContactClassTablePicture",1);
//ContactClassTable.setColumnExpandRatio("ContactClassTableName",3);
addItem(new Object[]{vNewImage, LabelContactName},1);
setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
addStyleName(ValoTheme.TABLE_BORDERLESS) ;
addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
setPageLength(this.size());
}

};