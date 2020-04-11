package org.programutvikling.gui;

import javafx.util.StringConverter;

public class Converter {

    public static class StringDoubleConverter extends StringConverter<Double> {
        private boolean conversionSuccessful;

        public double stringTilDouble(String s) {
            try {
                double result = Double.parseDouble(s);
                conversionSuccessful = true;
                return result;
            } catch (NumberFormatException e) {
                Dialog.showErrorDialog("Du må skrive inn et gyldig tall.");
                conversionSuccessful = false;
                return 0.0;
            }
        }

        public boolean wasSuccessful() {
            return conversionSuccessful;
        }


        @Override
        public String toString(Double aDouble) {
            return null;
        }

        @Override
        public Double fromString(String s) {
            return null;
        }


    }
}

