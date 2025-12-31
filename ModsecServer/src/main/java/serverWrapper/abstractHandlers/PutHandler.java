package serverWrapper.abstractHandlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface PutHandler {

    void handlePut(HttpExchange exchange) throws IOException;
}
