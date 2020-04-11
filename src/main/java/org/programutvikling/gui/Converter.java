package org.programutvikling.gui;

import javafx.util.converter.DoubleStringConverter;

public class Converter {

        public static class DoubleStringConverter extends javafx.util.converter.DoubleStringConverter {
            private boolean conversionSuccessful;

            public double stringTilDouble(String s) {
                try {
                    double result = super.fromString(s);
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

