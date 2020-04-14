package org.programutvikling.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ComponentValidator {


    static boolean isProductTypeOk(String inputStr){
        /*
        String[] items = ComponentTypes.getComponentTypesArray();
        boolean bol  = Arrays.stream(items).anyMatch(inputStr::contains);
        return bol && !inputStr.isBlank();*/
        return true;
      //items.parallelStream().anyMatch(inputStr::contains);
        }

        boolean isProductPriceOk(double price){
            return price>0;
        }
}
       /* isProductPriceMatchingAlreadySaved(ArrayList<Object> objects, String toBeSearched){

        }*/

