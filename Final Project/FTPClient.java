import java.io.*;
import java.net.*;

public class FTPClient{
    public static void main(String[] args) throws Exception{
        Socket mysocket=new Socket("localhost",1234);
        InputStream irs=mysocket.getInputStream();
        byte[] code=new byte[500000];
        irs.read(code,0,code.length);
        FileOutputStream fos =new FileOutputStream("D://to_this/msdia80.dll");
        fos.write(code,0,code.length);
        fos.close();
        irs.close();
        mysocket.close();
    }
}