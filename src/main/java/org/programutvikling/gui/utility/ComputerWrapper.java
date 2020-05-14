package org.programutvikling.gui.utility;

import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
//brukes for å fjerne duplikater fra computer, uten å måtte endre domain (pga deserialisering)

    class ComputerWrapper {
        private final Computer computerWrapper;

        public Computer getComputerWrapper() {
            return computerWrapper;
        }

        private final int hash;

        public ComputerWrapper(Computer data) {
            this.computerWrapper = data;
            int hashcodeInList = 0;
            for(Component c : data.getComponentRegister().getRegister()){
                hashcodeInList = hashcodeInList + c.getProductDescription().hashCode();
            }
            this.hash = data.getProductName().hashCode() + hashcodeInList;
        }

        @Override
        public int hashCode() {
            return this.hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ComputerWrapper)) return false;
            ComputerWrapper other = (ComputerWrapper)obj;
            return computerWrapper.getProductName().equals(other.getComputerWrapper().getProductName());
        }
    }
