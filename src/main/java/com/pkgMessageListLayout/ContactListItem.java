package com.pkgMessageListLayout;

import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dmitriy on 06.01.2018.
 */
public class ContactListItem extends Table
{
Image ContactPicture;
String ContactName;

public ContactListItem()
{
this(null,null);
}

public ContactListItem( Image NewContactPicture, String NewContactName )
{

Image vNewImage = new Image();
vNewImage.setSource(NewContactPicture.getSource());

vNewImage.setWidth(30, Unit.PIXELS);
vNewImage.setHeight(40, Unit.PIXELS);

Table ContactClassTable = new Table();
ContactClassTable.addContainerProperty("ContactClassTablePicture", Image.class, null);
ContactClassTable.addContainerProperty("ContactClassTableName", String.class, null);
//ContactClassTable.setColumnExpandRatio("ContactClassTablePicture",1);
//ContactClassTable.setColumnExpandRatio("ContactClassTableName",3);
ContactClassTable.addItem(new Object[]{vNewImage, NewContactName},1);

this.setContainerDataSource(ContactClassTable);
this.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
this.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
this.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
this.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
this.setPageLength(ContactClassTable.size());

}

};
