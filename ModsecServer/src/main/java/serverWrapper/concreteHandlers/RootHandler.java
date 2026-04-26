package serverWrapper.concreteHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import serverWrapper.CoreConstants;
import serverWrapper.abstractHandlers.GetHandler;
import java.io.IOException;

// Will probably delete this later
public class RootHandler extends BaseHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch(exchange.getRequestMethod()){

            default -> handleMethodNotAllowed(exchange);

        }

    }

}
