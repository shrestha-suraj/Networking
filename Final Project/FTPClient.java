import java.io.*;
import java.net.*;
import java.util.*;

public class FTPClient {
    public static void main(String[] args) throws Exception {
        Socket mysocket=new Socket ("localhost",6789);
        InputStream irs = mysocket.getInputStream();
        byte[] code = new byte[500000];
        irs.read(code, 0, code.length);
        FileOutputStream fos = new FileOutputStream("D:/user/answer.mp3");
        fos.write(code, 0, code.length);
        fos.close();
        irs.close();
        mysocket.close();
    }
}