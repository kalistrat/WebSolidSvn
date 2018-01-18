package com;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.security.MessageDigest;

/**
 * Created by kalistrat on 16.01.2018.
 */
public class staticMethods {

    static public final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static public final String DB_URL = "jdbc:oracle:thin:@0.0.0.0:1521/orcl";
    static public final String USER = "solid";
    static public final String PASS = "solidweb";

    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public static Double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber.replace(",", "."));
            } catch(Exception e) {
                return null;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return null;
    }

    public static Integer strToIntValue(String Sval) {

        try {
            //System.out.println(Sval);
            return Integer.parseInt(Sval);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
