package com.szamol.elibrary.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElibraryUtils {

    public static boolean checkExpression(String pattern, String expression) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expression);
        return m.matches();
    }
}
