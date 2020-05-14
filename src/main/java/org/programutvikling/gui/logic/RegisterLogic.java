package org.programutvikling.gui.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RegisterLogic {

    public static ArrayList<String> getDuplicateFreeList(ArrayList<String> list) {
        ArrayList<String> result = (ArrayList<String>) makeListUnique(list);
        return result;
    }

    private static List<String> makeListUnique(List<String> list) {
        //fungerer denne??
        List<String> result = new ArrayList<>(list);
        HashSet<String> set = new HashSet<>();
        for (String item : list) {
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }
}
