package org.programutvikling.component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ComponentValidator {
    private ComponentTypes componentTypes = new ComponentTypes();

    boolean isProductTypeOk(String inputStr, String[] items){
        return Arrays.stream(items).anyMatch(inputStr::contains);

      //items.parallelStream().anyMatch(inputStr::contains);

        }
    }

