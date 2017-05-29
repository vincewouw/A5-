/*
 * Avans Hogeschool
 * Academie voor Deeltijd
 * Opleiding Informatica
 * @author Frans Spijkerman
 */
package web;

import web.server.Server;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server(1080);
            server.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
