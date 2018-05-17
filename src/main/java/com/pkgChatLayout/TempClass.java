package com.pkgChatLayout;

//Вспомогательный класс
public class TempClass
{
// ID текущего пользователя
public static Integer current_user_id;

// ID пользователя кому отправляется сообщение
public static Integer second_user_id;

public static String FolderSeparateCharacter;
}

/*

select xmlelement ("messages", xmlagg(
xmlelement ("message"
,xmlelement ("from_user_name", su.second_name || ' ' || su.first_name  || ' ' || su.middle_name)
,xmlelement ("message_text",mes.message_text)
,xmlelement ("message_date" ,to_char(mes.message_date,'dd.mm.yyyy hh24:mi:ss'))
,xmlelement ("links", xmlagg (XMLFOREST(el.link_url, el.link_title)))
)))
from solid.message mes
join solid.system_users su on mes.from_user_id = su.user_id
left join solid.external_link el on mes.message_id = el.message_id
group by mes.message_text, mes.message_id, mes.message_date , su.second_name || ' ' || su.first_name  || ' ' || su.middle_name
order by mes.message_id asc, el.external_link_id asc

 */