package org.programutvikling.component;

import org.programutvikling.gui.ContextModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ComponentValidator {

    static boolean isComponentValid(Component component){
        //return true;
        return isProductTypeValid(component.getProductType()) && isProductNameValid(component.getProductName()) && isProductPriceValid(component.getProductPrice());
    }

    static boolean isComponentValid(String type, String name, String description, double price){
        //return true;
        return isProductTypeValid(type) && isProductNameValid(name) && isProductPriceValid(price);
    }

    public static boolean isComponentFromTxtValid(Component component){
        //må sjekke at typen eksisterer, og at prisen stemmer utifra navn.
        if(isProductTypeValid(component.getProductType()) && doesPriceMatchDatabase(component)){
            return true;
        }else{
            return false;
        }
    }

    private static boolean doesPriceMatchDatabase(Component component) {
        for(Component c : ContextModel.INSTANCE.getComponentRegister().getRegister()){
            if(c.getProductName().equals(component.getProductName())
                    && c.getProductType().equals(component.getProductType())){

                //kunne ha lagd til beskrivelse her også ,men whatever - (brukeren kan endre den hvis hen vil)
                if(c.getProductPrice() == component.getProductPrice()){
                    return true;
                }
            }
        }
        return false;
    }

    /*
       String[] items = ComponentTypes.getComponentTypesArray();
       return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
   */
    static boolean isProductTypeValid(String type){
        String[] c = ComponentTypes.getComponentTypesArray();
        if(type.isBlank()){
            System.out.println("blank string");
            return false;
        }
        for (String s : c) {
            if (type.toLowerCase().equals(s.toLowerCase())) {
                return true;
            }
        }
        System.out.println("produkttype-navnet fins ikke i databasen vår");
        return false;
        //items.parallelStream().anyMatch(inputStr::contains);
    }
        /*
        String[] items = ComponentTypes.getComponentTypesArray();
        boolean bol  = Arrays.stream(items).anyMatch(inputStr::contains);
        return bol && !inputStr.isBlank();
*/      static boolean isProductNameValid(String name){
            return name.length()<100;
        }

        static boolean isProductPriceValid(double price){
            return price>0;
        }
}
       /* isProductPriceMatchingAlreadySaved(ArrayList<Object> objects, String toBeSearched){

        }*/

