package org.programutvikling.domain.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComponentValidatorTest {

    @Test
    void isComponentValid() {
        Component component = new Component("", "", "", -1);
        assertFalse(ComponentValidator.isComponentValid(component));
    }

    @Test
    void isComponentInRegisterThenReturnIt() {
    }

    @Test
    void testIsComponentValid() {
    }

    @Test
    void isComponentFromTxtValid() {
    }

    @Test
    void doesPriceMatchDatabase() {
    }

    @Test
    void isProductTypeValid() {
    }

    @Test
    void isProductNameValid() {
    }

    @Test
    void isProductPriceValid() {
    }

    @Test
    void isProductDescriptionValid() {
    }

    @Test
    void checkPriceAgainstDatabaseGetPrice() {
    }

    @Test
    void isPrimaryKeyAMatch() {
    }
}