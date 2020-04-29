package org.programutvikling.component;

import org.programutvikling.gui.ContextModel;

public class ComponentValidator {

    static boolean isComponentValid(Component component) {
        //return true;
        return isProductTypeValid(component.getProductType()) && isProductNameValid(component.getProductName()) && isProductPriceValid(component.getProductPrice());
    }

    static boolean isComponentValid(String type, String name, String description, double price) {
        //return true;
        return isProductTypeValid(type) && isProductNameValid(name) && isProductPriceValid(price);
    }

    public static boolean isComponentFromTxtValid(Component component) {
        //må sjekke at typen eksisterer, og at prisen stemmer utifra navn.
        if (isProductTypeValid(component.getProductType()) && doesPriceMatchDatabase(component)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean doesPriceMatchDatabase(Component component) {
        for (Component c : ContextModel.INSTANCE.getComponentRegister().getRegister()) {
            if (isPrimaryKeyAMatch(component, c)) {

                //kunne ha lagd til beskrivelse her også ,men whatever - (brukeren kan endre den hvis hen vil)
                if (c.getProductPrice() == component.getProductPrice()) {
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
    static boolean isProductTypeValid(String type) {
        String[] c = ComponentTypes.getComponentTypesArray();
        if (type.isBlank()) {
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
*/
    static boolean isProductNameValid(String name) {
        return name.length() < 100;
    }

    static boolean isProductPriceValid(double price) {
        return price > 0;
    }


    //hvis productname og producttype fra tempcomponent matcher en component i registeret OG prisen ikke er lik -
// returner den riktige komponenten. hvis alt stemmer, return null.


    //todo denne metoden fungerer som den skal - men hvis databasen inneholder ting med samme navn+type - blir det
    // funnet "feil pris" hele tiden.
    public static double checkPriceAgainstDatabaseReturnComponent(Component tempComponent) {
        for (Component c : ContextModel.INSTANCE.getComponentRegister().getRegister()) {
            if (isPrimaryKeyAMatch(tempComponent, c)) {
                //hvis match på produkt - sjekk om prisen stemmer
                if (c.getProductPrice() != tempComponent.getProductPrice()) {
                    //hvis alt bortsett fra prisen stemmer - returner c
                    return c.getProductPrice();
                } else {
                    //prisen stemmer :
                    return -1.00;
                }
            }
        }
        //hvis ingenting stemmer - return null
        return -2.00;
    }

    private static boolean isPrimaryKeyAMatch(Component tempComponent, Component c) {
        return c.getProductName().equals(tempComponent.getProductName())
                && c.getProductType().equals(tempComponent.getProductType())
        && c.getProductDescription().equals(tempComponent.getProductDescription());

        //todo kan legge til beskrivelse som PK her - det er de feltene som sjekkes i en loop om stemmer
    }
}
       /* isProductPriceMatchingAlreadySaved(ArrayList<Object> objects, String toBeSearched){

        }*/

