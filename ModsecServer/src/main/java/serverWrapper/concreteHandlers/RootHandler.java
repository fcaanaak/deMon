package serverWrapper.concreteHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import serverWrapper.CoreConstants;
import serverWrapper.abstractHandlers.GetHandler;
import java.io.IOException;


public class RootHandler extends BaseHandler implements HttpHandler, GetHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch(exchange.getRequestMethod()){

            case CoreConstants.REQUEST_GET -> handleGet(exchange);

            default -> handleMethodNotAllowed(exchange);

        }

    }

    @Override
    public void handleGet(HttpExchange exchange) throws IOException {

        sendResponse(exchange,":)\n",CoreConstants.RESPONSE_OK);

    }
}
