/*
 * Avans Hogeschool
 * Academie voor Deeltijd
 * Opleiding Informatica
 * @author Frans Spijkerman
 */
package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * File wrapper
 * File with methods typically used in a web server
 */
public class MyFile {

    String fullPath;
    String[] pathParts;
    String name;
    String ext;
    FileInputStream inStream;

    /**
     * Get the input stream
     * @return
     * @throws FileNotFoundException 
     */
    private FileInputStream getFileInputStream() throws FileNotFoundException {
        if (inStream == null) {
            inStream = new FileInputStream(fullPath);
        }
        return inStream;
    }

    /**
     * Constructor
     * parse filename
     * @param path 
     */
    public MyFile(String path) {
        
        fullPath = path;
        if(fullPath.endsWith("/")) {
            fullPath += "index.html";
        }
        pathParts = fullPath.split("/");
        if (pathParts.length > 0) {
            name = pathParts[pathParts.length - 1];
        } else {
            name = "";
        }
    }

    @Override
    public String toString() {
        return fullPath;
    }

    /**
     * 
     * @return file name including extension
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return file extension, including dot
     */
    public String getExtension() {
        if (ext == null) {
            int pos = name.lastIndexOf('.');
            if (pos < 0) {
                ext = "";
            } else {
                ext = name.substring(pos);
            }
        }
        return ext;
    }

    /**
     * Read bytes from file
     * @param buffer: buffer to read to
     * @param maxlen: maximum number of bytes to read
     * @return number of bytes actually read, -1 means end of file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public long read(byte[] buffer, int maxlen) throws FileNotFoundException, IOException {
        getFileInputStream();
        return inStream.read(buffer, 0, maxlen);
    }

    /**
     * Get the content type, determined by file extension (may be unreliable)
     * @return content type as used in http headers
     */
    public String getContentType() {
        String lext = getExtension().toLowerCase();
        switch (lext) {
            case ".pdf":
                return "application/pdf";
            case ".htm":
            case ".html":
                return "text/html; charset=UTF-8";
            case ".gif":
                return "image/gif";
            case ".jpg":
            case ".jpeg":
                return "image/jpeg";
            default:
                return "application/octet-stream\r\nContent-Disposition: attachment; filename=\"" + getName() + "\"";
        }
    }

    public long getLength() throws FileNotFoundException, IOException {
        getFileInputStream();
        return inStream.getChannel().size();
    }

}
