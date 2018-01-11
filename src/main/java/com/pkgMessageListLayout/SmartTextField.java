package com.pkgMessageListLayout;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;

/**
* Created by Dmitriy on 10.01.2018.
*/

public class SmartTextField extends TextField
{
private Byte ContainFilterText;
private Byte ContainUserText;

/* SmartTextField Begin */

public SmartTextField()
{
this.clear();
ContainFilterText = 1;
ContainUserText = 0;

/* SmartTextField Events Begin*/

/* addContextClickListener */
this.addContextClickListener(new ContextClickEvent.ContextClickListener()
{
@Override public void contextClick(ContextClickEvent event)
{
// Есть пользовательский текст
if (ContainUserText == 1)
{

}
else  // Нет пользовательского текста
{
if (ContainFilterText == 1)
{
setValue("");
ContainFilterText = 0;
}
}
}
});
/* addContextClickListener */

/* addBlurListener (onLostFocus) */
this.addBlurListener(new FieldEvents.BlurListener()
{
public void blur(FieldEvents.BlurEvent event)
{
// Есть пользовательский текст
if (ContainUserText == 1)
{
}
else  // Нет пользовательского текста
{
if (ContainFilterText == 0)
{
setValue("Поиск..");
ContainFilterText = 1;
ContainUserText = 0;
}
}

}
});
/* addBlurListener */
/* addTextChangeListener */

this.addTextChangeListener(new FieldEvents.TextChangeListener()
{
@Override public void textChange(FieldEvents.TextChangeEvent textChangeEvent)
{
if (textChangeEvent.getText().equals(""))
{
ContainUserText = 0;
}
else
{
ContainFilterText = 0;
ContainUserText = 1;
}
}
});

/* addTextChangeListener */

/* SmartTextField Events End*/
}
/* SmartTextField End*/

public Byte GetContainFilterText()
{
return this.ContainFilterText;
}

public Byte GetContainUserText()
{
return this.ContainUserText;
}

};