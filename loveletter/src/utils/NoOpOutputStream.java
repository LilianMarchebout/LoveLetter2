package loveletter.src.utils;

import java.io.OutputStream;

class NoOpOutputStream extends OutputStream {
    @Override
    public void write(int b) {
        // Ne fait rien
    }
}
