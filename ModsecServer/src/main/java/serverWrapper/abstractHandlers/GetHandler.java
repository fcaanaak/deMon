package serverWrapper.abstractHandlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface GetHandler {

    void handleGet(HttpExchange exchange) throws IOException;

}
