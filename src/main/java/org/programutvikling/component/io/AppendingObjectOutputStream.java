package org.programutvikling.component.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;



//https://stackoverflow.com/questions/1194656/appending-to-an-objectoutputstream

public class AppendingObjectOutputStream extends ObjectOutputStream {

    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // do not write a header, but reset:
        reset();
    }

}
