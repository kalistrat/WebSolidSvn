package com.pkgMessageListLayout;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
* Created by Dmitriy on 06.01.2018.
*/

public class MessageListItem2 extends GridLayout
{
Image ContactImage;
String MessageText;
String ContactName;
String MessageDate;
VerticalLayout LinkLayout;

public MessageListItem2(Image vContactImage, String vMessageText, String vContactName, String vMessageDate, VerticalLayout vLinkLayout)
{
setColumns(1);
setRows(1);

Image vNewImage = new Image();
vNewImage.setSource(vContactImage.getSource());

vNewImage.setWidth(50, Unit.PIXELS);
vNewImage.setHeight(50, Unit.PIXELS);

Table MainTable = new Table();
MainTable.addContainerProperty("MainTableImageColumn", Image.class, null);
MainTable.addContainerProperty("MainTableNameTextColumn", Table.class, null);
MainTable.addContainerProperty("MainTableDateColumn", String.class, null);
MainTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
MainTable.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
MainTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
MainTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;

MainTable.setColumnExpandRatio("MainTableImageColumn",10);
MainTable.setColumnExpandRatio("MainTableNameTextColumn",80);
MainTable.setColumnExpandRatio("MainTableDateColumn",10);

MainTable.setWidth("100%");
MainTable.setPageLength(1);

Table SecondTable = new Table();
SecondTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
SecondTable.addContainerProperty("SecondTableColumn", String.class, null);
SecondTable.addItem(new Object[]{vContactName},1);
SecondTable.addItem(new Object[]{vMessageText},2);
SecondTable.setPageLength(2);

SecondTable.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
SecondTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
SecondTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
MainTable.addItem(new Object[]{vNewImage, SecondTable, vMessageDate},1);

this.addStyleName("hlayout-with-borders");

this.addComponent(MainTable,0,0);
if (vLinkLayout != null)
{
this.setRows(2);
this.addComponent(vLinkLayout,0,1);
}
setWidth("100%");

}
};
