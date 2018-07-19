package com.pkgChatLayout;

/*
https://stackoverflow.com/questions/691813/is-there-a-null-outputstream-in-java
*/

import java.io.IOException;
import java.io.OutputStream;

/**
* Created by Dmitriy on 13.07.2018.
*/

//Вспомогательный класс
public class NullOutputStream extends OutputStream
{
@Override public void write(int b) throws IOException
{
}
}