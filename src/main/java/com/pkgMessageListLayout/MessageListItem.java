package com.pkgMessageListLayout;

import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
* Created by Dmitriy on 06.01.2018.
*/

public class MessageListItem extends Table
{
Image ContactImage;
String MessageText;
String ContactName;
String MessageDate;

public MessageListItem()
{

}


public MessageListItem
(Image vContactImage, String vMessageText, String vContactName, String vMessageDate)
{
Image vNewImage = new Image();
vNewImage.setSource(vContactImage.getSource());

vNewImage.setWidth(40, Unit.PIXELS);
vNewImage.setHeight(40, Unit.PIXELS);

Table t1 = new Table();
t1.addContainerProperty("t1ImageColumn", Image.class, null);
t1.addContainerProperty("t1NameTextColumn", Table.class, null);
t1.addContainerProperty("t1DateColumn", String.class, null);

t1.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
t1.addStyleName(ValoTheme.TABLE_BORDERLESS) ;

t1.setPageLength(1);

Table t2 = new Table();
t2.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
t2.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
t2.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
//t2.addStyleName("components-inside");
t2.addContainerProperty("t2DateColumn", String.class, null);

t2.addItem(new Object[]{vContactName},1);
t2.addItem(new Object[]{vMessageText},2);
t2.setPageLength(2);

setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
addStyleName(ValoTheme.TABLE_BORDERLESS) ;
//this.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
//this.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
t1.addItem(new Object[]{vNewImage, t2, vMessageDate},1);
setContainerDataSource(t1);

setColumnWidth("setColumnWidth",10);
//this.setColumnWidth("setColumnWidth",15);
setColumnWidth("setColumnWidth",20);
//this.setWidthUndefined();
setWidth("100%");
setPageLength(1);
}

};