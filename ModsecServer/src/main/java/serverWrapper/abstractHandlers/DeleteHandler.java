package serverWrapper.abstractHandlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface DeleteHandler {

    void handleDelete(HttpExchange exchange) throws IOException;
}
