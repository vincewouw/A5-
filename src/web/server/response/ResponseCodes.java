/*
 * Avans Hogeschool
 * Academie voor Deeltijd
 * Opleiding Informatica
 * @author Frans Spijkerman
 */

package web.server.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Find text for numeric status code
 * according to https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
 */
public class ResponseCodes {

    private static ResponseCodes instance; // Singleton
    private final Map<Integer, String> messages = new HashMap<>();

    public static String getMessage(int code) {
        if (instance == null) {
            instance = new ResponseCodes();
        }
        return instance._getMessage(code);
    }

    private String _getMessage(int code) {
        if (messages.containsKey(code)) {
            return messages.get(code);

        } else {
            return "?";
        }
    }

    private ResponseCodes() {
        // Informational 1xx
        messages.put(100, "Continue");
        messages.put(101, "Switching Protocols");
        // Successful 2xx
        messages.put(200, "OK");
        messages.put(201, "Created");
        messages.put(202, "Accepted");
        messages.put(203, "Non-Authoritative Information");
        messages.put(204, "No Content");
        messages.put(205, "Reset Content");
        messages.put(206, "Partial Content");
        // Redirection 3xx
        messages.put(300, "Multiple Choices");
        messages.put(301, "Moved Permanently");
        messages.put(302, "Found");
        messages.put(303, "See Other");
        messages.put(304, "Not Modified");
        messages.put(305, "Use Proxy");
        messages.put(306, "(Unused)");
        messages.put(307, "Temporary Redirect");
        // Client Error 4xx
        messages.put(400, "Bad Request");
        messages.put(401, "Unauthorized");
        messages.put(402, "Payment Required");
        messages.put(403, "Forbidden");
        messages.put(404, "Not Found");
        messages.put(405, "Method Not Allowed");
        messages.put(406, "Not Acceptable");
        messages.put(407, "Proxy Authentication Required");
        messages.put(408, "Request Timeout");
        messages.put(409, "Conflict");
        messages.put(410, "Gone");
        messages.put(411, "Length Required");
        messages.put(412, "Precondition Failed");
        messages.put(413, "Request Entity Too Large");
        messages.put(414, "Request-URI Too Long");
        messages.put(415, "Unsupported Media Type");
        messages.put(416, "Requested Range Not Satisfiable");
        messages.put(417, "Expectation Failed");
        // Server Error 5xx
        messages.put(500, "Internal Server Error");
        messages.put(501, "Not Implemented");
        messages.put(502, "Bad Gateway");
        messages.put(503, "Service Unavailable");
        messages.put(504, "Gateway Timeout");
        messages.put(505, "HTTP Version Not Supported");
    }
}
