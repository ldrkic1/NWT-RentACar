package ba.unsa.etf.vehiclemicroservice.demo.Exception;

import javax.servlet.WriteListener;
import java.io.IOException;

public abstract class ServletOutputStream extends javax.servlet.ServletOutputStream {
    public abstract void write(int b) throws IOException;

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
