package org.programutvikling.gui.utility;

import java.util.*;

public class RegisterUtility {

    public static <T> List<T> removeDuplicates(List<T> inputList) {
        // overloader med 1 parameter, comparator: null betyr at den ikke sorter
        Set<T> s = new HashSet<T>(inputList);
        System.out.println(inputList.size());
        System.out.println(s.size());

        ArrayList<T> al = new ArrayList<T>(s);
        return al;
    }

    public static <T> void removeDuplicatesVoid(List<T> inputList) {
        // overloader med 1 parameter, comparator: null betyr at den ikke sorter
        Set<T> s = new HashSet<T>(inputList);
        System.out.println(inputList.size());
        System.out.println(s.size());

        ArrayList<T> al = new ArrayList<T>(s);
        inputList = new ArrayList<>(s);

    }

    public static <T> List<T> removeDuplicates(List<T> inputList, Comparator<? super T> comparator) {
        Set<T> set = new TreeSet<>(comparator);
        System.out.println(inputList.size());
        set.addAll(inputList);
        System.out.println(set.size());
        return new ArrayList<>(set);
    }
}
