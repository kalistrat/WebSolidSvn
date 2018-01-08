package pkgdima;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

/**
 * Created by Dmitriy on 05.01.2018.
 */
@Theme("mytheme")
public class MessageListLayout extends VerticalLayout
{

public MessageListLayout()
{
VerticalLayout vlayout1 = new VerticalLayout();
HorizontalSplitPanel HrSplitPanel = new HorizontalSplitPanel();

VerticalLayout vlayout2 = new VerticalLayout();

HorizontalLayout hlayout11 = new HorizontalLayout();
HorizontalLayout hlayout12 = new HorizontalLayout();
HorizontalLayout hlayout13 = new HorizontalLayout();

HorizontalLayout hlayout21 = new HorizontalLayout();
HorizontalLayout hlayout22 = new HorizontalLayout();
HorizontalLayout hlayout23 = new HorizontalLayout();

TextField ContactFilterTextField = new TextField("");
TextField MessageTextField = new TextField("");

ContactListTable ContactListTable1 = new ContactListTable();
Table MessageListTable = new Table();

vlayout1.setWidth("100%");
vlayout2.setWidth("100%");

ContactFilterTextField.addStyleName("no-caption");
hlayout12.addComponent(ContactFilterTextField);

Image NewContactPicture = new Image();
NewContactPicture.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
NewContactPicture.setWidth(30, Unit.PIXELS);
NewContactPicture.setHeight(30, Unit.PIXELS);

Image NewContactPicture1 = new Image();
NewContactPicture1.setSource(new ExternalResource("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
NewContactPicture1.setWidth(30, Unit.PIXELS);
NewContactPicture1.setHeight(30, Unit.PIXELS);

//FileResource resource = new FileResource(new File("https://docs.oracle.com/javaee/6/tutorial/doc/graphics/javalogo.png"));
ContactListItem NewContact = new ContactListItem (NewContactPicture, "Contact Name");
ContactListTable1.AddContactItem (NewContact);

ContactListItem NewContact1 = new ContactListItem (NewContactPicture1, "Contact Name1");
ContactListTable1.AddContactItem (NewContact1);
//ContactListTable1.setPageLength(ContactListTable1.GetRecordCount());

//ContactListTable.setPageLength(1);

hlayout13.addComponent(ContactListTable1);

hlayout11.setWidth("100%");
hlayout12.setWidth("100%");
hlayout13.setWidth("100%");

hlayout21.setWidth("100%");
hlayout22.setWidth("100%");
hlayout23.setWidth("100%");

MessageTextField.setWidth("100%");
hlayout23.addComponent(MessageTextField);

vlayout1.addComponent(hlayout11);
vlayout1.addComponent(hlayout12);
vlayout1.addComponent(hlayout13);

vlayout2.addComponent(hlayout21);
vlayout2.addComponent(hlayout22);
vlayout2.addComponent(hlayout23);


hlayout11.addStyleName("blue-layout");
hlayout21.addStyleName("blue-layout");

HrSplitPanel.addComponent(vlayout1);
HrSplitPanel.addComponent(vlayout2);
HrSplitPanel.setSplitPosition(20, Unit.PERCENTAGE);
//HrSplitPanel.setLocked(true);
this.addComponent(HrSplitPanel);



/*
this.addListener( new AbstractSplitPanel.SplitPositionChangeListener()
 {
 @Override  public void onSplitPositionChanged(AbstractSplitPanel.SplitPositionChangeEvent event)
 {
 vlayout1.requestRepaint();
 vlayout2.requestRepaint();
 hlayout11.requestRepaint();
 hlayout12.requestRepaint();
 hlayout13.requestRepaint();
         }
    } )

/*


 */
}



/*







//Table MainTable = new Table ();
//Table ContactListTable = new Table ();
//Table MessageListTable = new Table ();
//VerticalLayout vlayout1 = new VerticalLayout();
//VerticalLayout vlayout2 = new VerticalLayout();
///HorizontalLayout hlayout11 = new HorizontalLayout();/
//HorizontalLayout hlayout12 = new HorizontalLayout();
//HorizontalLayout hlayout13 = new HorizontalLayout();


//Table  LeftTable = new Table("");


public MessageListLayout()
{
//vlayout1.setHeight(100, Unit.PERCENTAGE);
//vlayout2.setHeight(100, Unit.PERCENTAGE);

vlayout1.setStyleName("hlayout11");
vlayout2.setStyleName("hlayout11");

vlayout1.addComponent(hlayout11);
vlayout1.addComponent(hlayout12);
vlayout1.addComponent(hlayout13);

vlayout1.addComponent(hlayout21);
vlayout1.addComponent(hlayout22);
vlayout1.addComponent(hlayout23);



HrSplitPanel.addComponent(vlayout1);
HrSplitPanel.addComponent(vlayout2);
HrSplitPanel.setSplitPosition(50, Unit.PERCENTAGE );

this.addComponent(HrSplitPanel);


/*

//hlayout11.addComponent(Panel1);
//hlayout12.addComponent(new Table("ddd"));
//hlayout13.addComponent(new Panel());





vlayout1.addStyleName("yellow-panel");
vlayout2.addStyleName("red-panel");
//hlayout13.addStyleName("yellow-panel");
//hlayout13.setHeight("50px");
//hlayout12.setHeight("50px");
//hlayout11.setHeight("50px");



//content.addComponent(new TextField("Participant"));
//content.addComponent(new TextField("Organization"));




*/

/*
vlayout1.addStyleName("layout-with-border");
vlayout1.addComponent(new Table ("fgfg"));

vlayout1.addComponent(hlayout11);
hlayout11.addStyleName("layout-with-border");
vlayout1.addComponent(hlayout12);
hlayout12.addStyleName("layout-with-border");
vlayout1.addComponent(hlayout13);
hlayout13.addStyleName("layout-with-border");










    /*
    LeftTable.setWidth("100%");
    LeftTable.addContainerProperty("LeftTableColumn", Table.class, null);
    LeftTable.setPageLength(LeftTable.size());
    LeftTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);

    Table LeftTableColumnRow1 = new Table("4");
    LeftTableColumnRow1.addContainerProperty("LeftTableColumnRow1Column", Table.class, null);
    LeftTableColumnRow1.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
    LeftTableColumnRow1.setPageLength(LeftTableColumnRow1.size());

    Table LeftTableColumnRow2 = new Table();
    LeftTableColumnRow2.addContainerProperty("LeftTableColumnRow2Column", Table.class, null);
    LeftTableColumnRow2.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
    LeftTableColumnRow2.setPageLength(LeftTableColumnRow2.size());

    Table LeftTableColumnRow3 = new Table();
    LeftTableColumnRow3.addContainerProperty("LeftTableColumnRow3Column", Table.class, null);
    LeftTableColumnRow3.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
    LeftTableColumnRow3.setPageLength(LeftTableColumnRow3.size());


    //LeftTable.addItem(new Object[]{LeftTableColumnRow1}, 0);
    //LeftTable.addItem(new Object[]{LeftTableColumnRow2}, 1);
    //LeftTable.addItem(new Object[]{LeftTableColumnRow3}, 2);

    vlayout1.addComponent(LeftTable);


   */
 /*
    //Основная таблица - MainTable
    MainTable.setWidth("100%");
    MainTable.addContainerProperty("MainTableContactListColumn", Table.class, null);
    MainTable.addContainerProperty("MainTableMessageListColumn", Table.class, null);

    MainTable.setColumnExpandRatio("MainTableContactListColumn",1);
    MainTable.setColumnExpandRatio("MainTableMessageListColumn",4);
    //MainTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

    //ContactListTable - Таблица для колонки со списком контактов
    ContactListTable.addContainerProperty("ContactListTableColumn", Table.class, null);
    ContactListTable.setPageLength(ContactListTable.size());
    ContactListTable.setWidth("100%");
    ContactListTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

    //ContactListTable - Таблица для колонки со списком сообщений
    MessageListTable.addContainerProperty("MessageListTableColumn", Table.class, null);
    MessageListTable.setPageLength(MessageListTable.size());
    MessageListTable.setWidth("100%");
    MessageListTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
    //      MessageListTable.addStyleName("TABLE_NO_HORIZONTAL_LINES");
//        MessageListTable.addStyleName("TABLE_NO_VERTICAL_LINES");

    MainTable.addItem(new Object[]{ContactListTable,MessageListTable}, 0);




}
}
*/

}

