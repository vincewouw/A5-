/*
 * Avans Hogeschool
 * Academie voor Deeltijd
 * Opleiding Informatica
 * @author Frans Spijkerman
 */
package web.server;

import web.server.request.RequestHandler;
import java.io.IOException;
import java.net.*;

public class Server {

    private final ServerSocket listenSocket;

    public Server(int port) throws Exception {
        listenSocket = new ServerSocket(port);
    }

    public void start() {
        try {
            while (true) {
                // De blocking call is de accept-methode: Een request
                // vanuit een browser resulteert hier in een communicatiesocket
                Socket communicationSocket = listenSocket.accept();
                // Start een handler met deze socket
                RequestHandler handler = new RequestHandler(communicationSocket);
                handler.start();
                // En ga meteen klaar staan voor het volgende request
            }
        } catch (IOException e) {
            // TO DO: iets moois
        }
    }
}
