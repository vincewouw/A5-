/*
 * Avans Hogeschool
 * Academie voor Deeltijd
 * Opleiding Informatica
 * @author Frans Spijkerman
 */
package web.server.request;

class BadRequestException extends Exception {

    private final String message;

    BadRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}