package serverWrapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;


import serverWrapper.concreteHandlers.ReportHandler;
import serverWrapper.concreteHandlers.RootHandler;

public class ServerWrapper {

    private HttpServer server;

    private void createContexts(){
        server.createContext(CoreConstants.PATH_REPORTS, new ReportHandler());

    }

    private void startServer(){
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
    }


    public ServerWrapper(){

        try {
            // Create an HttpServer instance
            server = HttpServer.create(new InetSocketAddress(CoreConstants.PORT), 0);

            createContexts();

            startServer();

            System.out.println("Server is running on port 3000");

        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }



}
