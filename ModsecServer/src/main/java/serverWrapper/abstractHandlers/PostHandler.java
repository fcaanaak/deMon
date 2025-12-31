package serverWrapper.abstractHandlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface PostHandler {

    void handlePost(HttpExchange exchange) throws IOException;
}
