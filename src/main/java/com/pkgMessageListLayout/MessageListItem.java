package com.pkgMessageListLayout;


import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dmitriy on 15.01.2018.
 */
public class MessageListItem extends GridLayout
{
Image ContactImage;
String MessageText;
String ContactName;
String MessageDate;
VerticalLayout LinkLayout;
Integer IncomingMesage;

public MessageListItem(Image vContactImage, String vMessageText, String vContactName, String vMessageDate, VerticalLayout vLinkLayout, Integer vIncomingMesage)
{
Image vNewImage = new Image();

if (vContactImage!= null)
{
vNewImage.setSource(vContactImage.getSource());
vNewImage.setWidth(40, Unit.PIXELS);
vNewImage.setHeight(40, Unit.PIXELS);
}
else
{
vNewImage=null;
}

GridLayout MainGrid = new GridLayout(3,2);

Table PictureTable = new Table();
PictureTable.addContainerProperty("PictureTableColumn", Image.class, null);
PictureTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
PictureTable.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
PictureTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
PictureTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
PictureTable.setWidth("80px");
PictureTable.addItem(new Object[]{vNewImage},1);
PictureTable.setPageLength(1);

Table NameAndDateTable = new Table();
NameAndDateTable.addContainerProperty("NameAndDateTableNameColumn", Label.class, null);
NameAndDateTable.addContainerProperty("NameAndDateTableDateColumn", String.class, null);
NameAndDateTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
NameAndDateTable.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
NameAndDateTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
NameAndDateTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
NameAndDateTable.setColumnWidth("NameAndDateTableNameColumn",600);
NameAndDateTable.setColumnWidth("NameAndDateTableDateColumn",150);
//NameAndDateTable.setWidth("100%");

Label ContactNameLabel = new Label(vContactName);

if (vIncomingMesage == 1)
{
ContactNameLabel.addStyleName("IncomingMesageContactNameLabel");
}

NameAndDateTable.addItem(new Object[]{ContactNameLabel , vMessageDate},1);
NameAndDateTable.setPageLength(1);

Table MessageTable = new Table();
MessageTable.addContainerProperty("MessageTableColumn", Label.class, null);
MessageTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
MessageTable.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
MessageTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
MessageTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
MessageTable.setWidth("100%");

Label MessageTextLabel = new Label(vMessageText);
MessageTextLabel.addStyleName("text-wrapping");

MessageTable.addItem(new Object[]{MessageTextLabel},1);
MessageTable.setPageLength(1);

MainGrid.addComponent(PictureTable,0,0,0,1);
MainGrid.addComponent(NameAndDateTable,1,0,2,0);
MainGrid.addComponent(MessageTable,1,1,2,1);

setRows(1);
setColumns(1);
addStyleName("hlayout-with-borders");

if (vLinkLayout != null)
{
MainGrid.setRows(3);
MainGrid.addComponent(vLinkLayout,0,2,1,2);
}


this.addComponent(MainGrid);
setWidth("100%");


}
}


