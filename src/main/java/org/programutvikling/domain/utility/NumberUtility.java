package org.programutvikling.domain.utility;

public class NumberUtility {

    public static int getNumberOfDigits(double d){
        String ds = String.valueOf(d);
        return ds.length() - 1;

    }
}
