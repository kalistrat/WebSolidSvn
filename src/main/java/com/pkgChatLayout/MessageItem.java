package com.pkgChatLayout;

import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MessageItem extends VerticalLayout
{
String ContactPicturePath;
String MessageText;
String ContactName;
String MessageDate;
VerticalLayout LinkLayout;
Boolean IncomingMesage;

public  MessageItem (Resource ResContactImage, String vMessageText, String vContactName, String vMessageDate, VerticalLayout vLinkLayout, Boolean vIncomingMesage)
{

/* Схема слоев
[1.1][1.2]



[1.2.A.1] [1.2.A.2] [1.2.A.3]
[           1.2.B           ]
[            1.3           ]
*/

// 1 Верхний слой
HorizontalLayout MainLayout = new HorizontalLayout();
//MainLayout.setWidth("200px");
//MainLayout.setHeight("20px");

//1.1  Аватар
Image ContactImage = new Image();

if (ResContactImage!=null)
{
ContactImage.setSource(ResContactImage);
ContactImage.setWidth(60, Unit.PIXELS);
ContactImage.setHeight(60, Unit.PIXELS);
}
else
{
ContactImage=null;
}

/* 1.2 Фио + Дата + Сообщение + слой внешних ссылок - BEGIN */
VerticalLayout MainLayoutMessagePart = new VerticalLayout();

//1.2.A ФИО и дата
//1.2.A.1 - ФИО
//1.2.A.2 - Null метка
//1.2.A.3 - дата

HorizontalLayout  MessagePartNameAndDate = new HorizontalLayout();
Label ContactNameLabel = new Label(vContactName);

if (vIncomingMesage == true)
{
ContactNameLabel.addStyleName("IncomingMessageContactNameLabel");
}
else
{
ContactNameLabel.addStyleName("OutgoingMessageContactNameLabel");
}

Label NullLabel = new Label();
Label DateLabel = new Label(vMessageDate);

MessagePartNameAndDate.addComponent(ContactNameLabel);
MessagePartNameAndDate.addComponent(NullLabel);
NullLabel.setWidthUndefined();
MessagePartNameAndDate.addComponent(DateLabel);

//1.2.B Текст сообщения
VerticalLayout MessagePartMessage  = new VerticalLayout();

Label MessageTextLabel = new Label("<br>" +  vMessageText, ContentMode.HTML);
MessageTextLabel.setWidth("700px");
MessageTextLabel.addStyleName("text-wrapping");

MessagePartMessage.addComponent(MessageTextLabel);
/* 1.2 Фио + Дата + Сообщение + слой внешних ссылок - END */

//Верхняя часть MainLayoutMessagePart = ФИО/Дата + Сообщение
MainLayoutMessagePart.addComponent(MessagePartNameAndDate);
MainLayoutMessagePart.addComponent(MessagePartMessage);

// 1.3 - Слой внешних ссылок
if (vLinkLayout != null)
{
MainLayoutMessagePart.addComponent(new HorizontalLayout());
MainLayoutMessagePart.addComponent(vLinkLayout);
}

//Сборка левого и правого слоёв
MainLayout.addComponent(ContactImage);
MainLayout.addComponent(MainLayoutMessagePart);

this.addComponent(MainLayout);
}
};
