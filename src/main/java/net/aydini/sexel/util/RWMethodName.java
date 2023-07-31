package net.aydini.sexel.util;

import java.lang.reflect.Field;


public final class RWMethodName
{
    
    private final static String READER_PREFIX = "get";
    private final static String boolean_READER_PREFIX = "is";
    private final static String WRITER_PREFIX = "set";
    
    public static String createReaderMethodName(Field field)
    {
        if(field.getType().equals(boolean.class))
            return boolean_READER_PREFIX + toUpperFirstLetter(field.getName());
        return READER_PREFIX  + toUpperFirstLetter(field.getName());
    }
    
    public static String createWriterMethodName(Field field)
    {
        return WRITER_PREFIX + toUpperFirstLetter(field.getName());
    }
    
    
    
    public static String toLowerFirstLetter(String string)
    {
        if (string == null ) throw new NullPointerException("empty string");
        else if (string.isEmpty()) return string;
        else if (string.length() == 1) return string.toLowerCase();
        else return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }
    
    
    public static String toUpperFirstLetter(String string)
    {
        if (string == null ) throw new NullPointerException("empty string");
        else if (string.isEmpty()) return string;
        else if (string.length() == 1) return string.toUpperCase();
        else return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

}