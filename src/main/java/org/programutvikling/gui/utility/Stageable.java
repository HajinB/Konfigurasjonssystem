package org.programutvikling.gui.utility;

import org.programutvikling.domain.utility.Clickable;


//brukes for å abstrahere registry(logikk) klassene.
public interface Stageable {
    void editClickableFromPopup(Clickable c);
}
