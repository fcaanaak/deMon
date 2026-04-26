package serverWrapper.concreteHandlers;

import com.sun.net.httpserver.HttpExchange;
import serverWrapper.CoreConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BaseHandler {

    protected void handleMethodNotAllowed(HttpExchange exchange) throws IOException {

        String response = "Method " + exchange.getRequestMethod() + " is not supported for this URI\n";

        exchange.sendResponseHeaders(CoreConstants.RESPONSE_METHOD_NOT_ALLOWED, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    protected void sendResponse(HttpExchange exchange, String message, int responseCode) throws IOException {

        String response = message;

        exchange.sendResponseHeaders(responseCode, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    protected String getRequestBody(HttpExchange exchange) throws IOException {

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuilder requestBody = new StringBuilder();

        while ((line = br.readLine()) != null) {
            requestBody.append(line);
        }

        br.close();
        isr.close();

        return String.valueOf(requestBody);

    }


}
