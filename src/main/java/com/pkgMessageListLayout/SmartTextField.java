package com.pkgMessageListLayout;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;

/**
* Created by Dmitriy on 10.01.2018.
*/

public class SmartTextField extends TextField
{
private boolean ContainFilterText;
private boolean ContainUserText;

/* SmartTextField Begin */

public SmartTextField()
{
clear();
setImmediate(true);
ContainFilterText = true;
ContainUserText = false;

/* SmartTextField Events Begin*/

/* addBlurListener (onLostFocus) */
addBlurListener(new FieldEvents.BlurListener()
{
public void blur(FieldEvents.BlurEvent event)
{
// Есть пользовательский текст
if (ContainUserText == true)
{
}
else  // Нет пользовательского текста
{
if (ContainFilterText == false)
{
setValue("Поиск..");
ContainFilterText = true;
ContainUserText = false;
}
}

}
});

/* addBlurListener */

/* addTextChangeListener */

addTextChangeListener(new FieldEvents.TextChangeListener()
{
@Override public void textChange(FieldEvents.TextChangeEvent textChangeEvent)
{
if (textChangeEvent.getText().equals(""))
{
ContainUserText = false;
}
else
{
ContainFilterText = false;
ContainUserText = true;
}
}
});

/* addTextChangeListener */

/* addFocusListener */

addFocusListener(new FieldEvents.FocusListener()
{
@Override public void focus(FieldEvents.FocusEvent focusEvent)
{
// Есть пользовательский текст
if (ContainUserText == true)
{

}
else  // Нет пользовательского текста
{
if (ContainFilterText == true)
{
setValue("");
ContainFilterText = false;
}
}

}
}

);
/* addFocusListener */

/* SmartTextField Events End*/
}
/* SmartTextField End*/

public boolean GetContainFilterText()
{
return this.ContainFilterText;
}

public boolean GetContainUserText()
{
return this.ContainUserText;
}

};