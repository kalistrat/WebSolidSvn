package com.pkgChatLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

/**
* Created by Dmitriy on 05.01.2018.
*/

@Theme("mytheme")

public class ChatLayout extends VerticalLayout
{
public ChatLayout()
{
ContactListLayout ContactListLayout1 = new ContactListLayout();
MessageListLayout MessageListLayout1 = new MessageListLayout();

HorizontalSplitPanel HrSplitPanel = new HorizontalSplitPanel();
HrSplitPanel.addComponent(ContactListLayout1);
HrSplitPanel.addComponent(MessageListLayout1);
HrSplitPanel.setSplitPosition(20, Unit.PERCENTAGE);
//HrSplitPanel.setLocked(true);

addComponent(HrSplitPanel);
}

}
