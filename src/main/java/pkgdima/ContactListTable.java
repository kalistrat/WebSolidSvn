package pkgdima;

import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;


/**
 * Created by Dmitriy on 07.01.2018.
 */

public class ContactListTable extends Table
{
private Integer RecordCount ;

public ContactListTable ()
{
Table t1 = new Table("");

t1.addContainerProperty("ContactListTableColumn1",ContactListItem.class, null);
t1.addStyleName("components-inside");
t1.setWidth("100%");
this.RecordCount = 0;
this.setContainerDataSource(t1);
this.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
this.setSizeUndefined();
}

public  Integer GetRecordCount()
{
return this.RecordCount;
}

public void AddContactItem ( ContactListItem NewContact)
{
this.addItem(new Object[]{NewContact}, this.RecordCount + 1);
this.RecordCount = this.RecordCount + 1;
this.setSizeUndefined();
}
}
