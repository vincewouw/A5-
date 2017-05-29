/*
 * Avans Hogeschool
 * Academie voor Deeltijd
 * Opleiding Informatica
 * @author Frans Spijkerman
 */
package web.server.request;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

class Request {

    private final String line;
    private final String command, path, protocol;

    Request(InputStream sock_in) throws IOException, BadRequestException {
        // Strings lezen gaat het gemakkelijkst via een BufferedReader
        BufferedReader in = new BufferedReader(new InputStreamReader(sock_in));

        // Lees de eerste regel, bijvoorbeeld "GET index.php HTTP/1.1"
        line = in.readLine();

        // Lees de rest van de request header
        while (!in.readLine().equals("")) {
            // Doe ik nog niks mee!
        }

        // Volgens protocol bestaat regel 1 uit drie delen, gescheiden door spaties.
        // (de browser moet spaties in het derde deel netjes door %20 vervangen)
        String[] parts = line.split(" ", 3);
        if (parts.length != 3) {
            throw new BadRequestException("Syntax error in request: " + line);
        }
        command = parts[0];
        path = parts[1];
        protocol = parts[2];
        if (!command.equals("GET")) {
            throw new BadRequestException("Unknown request: " + command);
        }
    }

    @Override
    public String toString() {
        return line;
    }

    String getCommand() {
        return command;
    }

    String getPath() {
        return path;
    }

    String getProtocol() {
        return protocol;
    }
}
