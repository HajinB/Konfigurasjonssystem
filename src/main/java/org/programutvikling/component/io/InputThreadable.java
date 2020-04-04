package org.programutvikling.component.io;

import org.programutvikling.component.ComponentRegister;

import java.nio.file.Path;

public interface InputThreadable {
    void openFileThread(ComponentRegister komponentregister, Path filePath);

    String call();
}
