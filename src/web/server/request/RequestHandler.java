/*
 * Avans Hogeschool
 * Academie voor Deeltijd
 * Opleiding Informatica
 * @author Frans Spijkerman
 */
package web.server.request;

/**
 * Een thread voor het lezen en afhandelen van een http-request
 */
import java.net.*;
import java.io.*;
import utils.MyFile;
import web.server.response.ResponseCodes;

public class RequestHandler extends Thread {

    private final Socket sock;
    private InputStream sock_in;
    private OutputStream sock_out;

    private Request request;

    private final String webroot = "C:/webroot";

    public RequestHandler(Socket sock) {
        this.sock = sock;
    }

    /**
     * Handle a http-request
     */
    @Override
    public void run() {
        try {
            sock_in = sock.getInputStream();
            sock_out = sock.getOutputStream();
            request = new Request(sock_in);
            
            MyFile myfile = new MyFile(webroot + request.getPath());
            
            
            writeFile(myfile);
            sock.close();
        } catch (BadRequestException e) {
            writeError(400);
        } catch (FileNotFoundException e) {
            writeError(404);
        } catch (IOException e) {
            writeError(500);
        }
    }

    /**
     * Write error message to console
     * And TRY to write a complete error response to the client (header + data)
     * (errors ignored)
     *
     * @param status: like 200 or 404
     */
    private void writeError(int status) {
        
        String text = status + " " + ResponseCodes.getMessage(status) + ":\r\n" + request;
        
        System.err.println(text);
        
        byte[] buffer = text.getBytes();
        try {
            writeHeader(status, "text/plain", buffer.length);
            write(buffer);
        } catch (IOException e) {
            // ignore error
        }
       
    }

    /**
     * Write a response header to client
     *
     * @param status: like 200 or 404
     * @param contentType: like "text/html" or "img/jpeg"
     * @param contentLength: lenght of following data IN BYTES
     */

    private void writeHeader(int status, String contentType, long contentLength) throws IOException {
        String message = ResponseCodes.getMessage(status);
        write("HTTP/1.0 " + status + " " + message + "\r\n");
        write("Content-Type: " + contentType + "\r\n");
        write("Content-Length: " + contentLength + "\r\n");
        write("Connection: close " + contentLength + "\r\n");
        write("\r\n"); // altijd met een lege regel eindigen
    }

    /**
     * Write reponse header + file contents to client
     *
     * @param file_in: open FileInputStream
     * @throws IOException
     */
    private void writeFile(MyFile myfile) throws IOException {
        writeHeader(200, myfile.getContentType(), myfile.getLength());
        byte[] buffer = new byte[1024];
        while (myfile.read(buffer, buffer.length) > 0) {
            write(buffer);
        }
    }

    /**
     * Write text to client
     *
     * @param text
     */
    private void write(String text) throws IOException {
        sock_out.write(text.getBytes());
    }

    /**
     * Write binary data to client
     *
     * @param data
     */
    private void write(byte[] data) throws IOException {
        sock_out.write(data);
    }

}
