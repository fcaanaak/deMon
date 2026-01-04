package serverWrapper.concreteHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import serverWrapper.CoreConstants;
import serverWrapper.abstractHandlers.GetHandler;
import serverWrapper.abstractHandlers.PostHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportHandler extends BaseHandler implements HttpHandler, GetHandler, PostHandler {

    List<JSONObject> latestReports = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()){

            case CoreConstants.REQUEST_GET -> handleGet(exchange);
            case CoreConstants.REQUEST_POST -> handlePost(exchange);

            default -> handleMethodNotAllowed(exchange);
        }
    }

    @Override
    public void handleGet(HttpExchange exchange) throws IOException {

        JSONArray jsonReports = new JSONArray(latestReports.reversed());
        latestReports.clear();

        sendResponse(exchange, jsonReports + "\n",CoreConstants.RESPONSE_OK);

    }

    @Override
    public void handlePost(HttpExchange exchange) throws IOException {

        String requestBodyString = getRequestBody(exchange);


        latestReports.add(new JSONObject(requestBodyString));


        sendResponse(exchange, requestBodyString + "\n",CoreConstants.RESPONSE_OK);

    }
}
