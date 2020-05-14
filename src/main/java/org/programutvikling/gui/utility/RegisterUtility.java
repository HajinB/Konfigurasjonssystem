package org.programutvikling.gui.utility;

import org.programutvikling.domain.computer.Computer;
import org.programutvikling.model.ModelEndUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegisterUtility {

    //fungerer i hovedsak kun hvis det er implementert equals og hashcode metoder for domainobjects.
    public static <T> List<T> removeDuplicates(List<T> inputList) {
        // overloader med 1 parameter, comparator: null betyr at den ikke sorter
        Set<T> s = new HashSet<T>(inputList);
        ArrayList<T> al = new ArrayList<T>(s);
        return al;
    }
}
