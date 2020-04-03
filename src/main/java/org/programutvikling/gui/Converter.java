package org.programutvikling.gui;

public class Converter {

        public static class DoubleStringConverter {
            private boolean conversionSuccessful;

            public double stringTilDouble(String s) {
                try {
                    double result = Double.parseDouble(s);
                    conversionSuccessful = true;
                    return result;
                } catch(NumberFormatException e) {
                    Dialog.showErrorDialog("Du må skrive inn et gyldig tall.");
                    conversionSuccessful = false;
                    return 0.0;
                }
            }

            public boolean wasSuccessful() {
                return conversionSuccessful;
            }
        }


    }

