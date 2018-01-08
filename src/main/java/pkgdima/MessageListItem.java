package pkgdima;

import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
* Created by Dmitriy on 06.01.2018.
*/

public class MessageListItem extends Table
{
Image ContractImage;
String MessageText;
String ContactName;
String MessageDate;

public MessageListItem()
{
this(null,null,null,null);
}

public MessageListItem
(Image vContractImage, String vMessageText, String vContactName, String vMessageDate)
{

Table t1 = new Table();
t1.addContainerProperty("t1ImageColumn", Image.class, null);
t1.addContainerProperty("t1NameTextColumn", Table.class, null);
t1.addContainerProperty("t1DateColumn", String.class, null);

t1.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
t1.addStyleName(ValoTheme.TABLE_BORDERLESS) ;

t1.setColumnExpandRatio("t1ImageColumn",1);
t1.setColumnExpandRatio("t1NameTextColumn",8);
t1.setColumnExpandRatio("t1DateColumn",1);

Table t2 = new Table();

t2.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
t2.addStyleName(ValoTheme.TABLE_BORDERLESS) ;

t2.addContainerProperty("t2DateColumn", String.class, null);
t2.addItem(new Object[]{vContactName},1);
t2.addItem(new Object[]{vMessageText},2);
this.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
this.addStyleName(ValoTheme.TABLE_BORDERLESS) ;
//this.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES) ;
//this.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES) ;
t1.addItem(new Object[]{vContractImage, t2, vMessageDate},1);
this.setContainerDataSource(t1);
this.setWidth("100%");
this.setPageLength(1);
}

};