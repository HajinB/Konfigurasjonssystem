package org.programutvikling.gui.utility;

import java.util.*;

public class RegisterUtility {

    //fungerer i hovedsak kun hvis det er implementert equals og hashcode metoder for domainobjects.
    public static <T> List<T> removeDuplicates(List<T> inputList) {
        // overloader med 1 parameter, comparator: null betyr at den ikke sorter
        Set<T> s = new HashSet<T>(inputList);
        System.out.println(inputList.size());
        System.out.println(s.size());

        ArrayList<T> al = new ArrayList<T>(s);
        return al;
    }
}
