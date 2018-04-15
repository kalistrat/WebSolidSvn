package com.pkgMessageListLayout;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.FileResource;

public class MessageItem extends VerticalLayout
{
Image ContactImage;
String MessageText;
String ContactName;
String MessageDate;
VerticalLayout LinkLayout;
Boolean IncomingMesage;

public  MessageItem (FileResource ResContactImage, String vMessageText, String vContactName, String vMessageDate, VerticalLayout vLinkLayout, Boolean vIncomingMesage)
{

/* Схема слоев
[1.1][1.2]
[    2   ]

[1.2.A.1] [1.2.A.2] [1.2.A.3]
[           1.2.B           ]
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

/* 1.2 Фио + Дата + Сообщение - BEGIN */
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

Label MessageTextLabel = new Label(vMessageText);
MessageTextLabel.setWidth("700px");
MessageTextLabel.addStyleName("text-wrapping");

MessagePartMessage.addComponent(MessageTextLabel);
/* 1.2 Фио + Дата + Сообщение - END*/

//Верхняя часть MainLayoutMessagePart = ФИО/Дата + Сообщение
MainLayoutMessagePart.addComponent(MessagePartNameAndDate);
MainLayoutMessagePart.addComponent(MessagePartMessage);

//Сборка верхнего слоя
MainLayout.addComponent(ContactImage);
MainLayout.addComponent(MainLayoutMessagePart);

// 2 Нижний слой - внешние ссылки
VerticalLayout LinkLayout = new VerticalLayout();

if (vLinkLayout != null)
{

LinkLayout.addComponent(vLinkLayout);
}
//LinkLayout.setWidth("200px");
//LinkLayout.setHeight("20px");

// Сборка 1 и 2 слоя
this.addComponent(MainLayout);
this.addComponent(LinkLayout);

}

};

