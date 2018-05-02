package com.pkgChatLayout;

/**
 * Created by Dmitriy on 02.05.2018.
 */

public class ContactRecord
{
String FIO;
String PhotoPath;
Integer SubjectId;
Integer OrderNum;

public ContactRecord(String vFIO,  String vPhotoPath, Integer vSubjectId, Integer vOrderNum)
{
FIO = vFIO;
PhotoPath = vPhotoPath;
SubjectId = vSubjectId;
OrderNum = vOrderNum;
}

}
