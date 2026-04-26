package serverWrapper;

public class CoreConstants {

    public static final int PORT = 3000;
    public static final int SOCKET_PORT = 8000;

    // Http Requests
    public static final String REQUEST_GET = "GET";
    public static final String REQUEST_POST = "POST";
    public static final String REQUEST_PUT = "PUT";
    public static final String REQUEST_DELETE = "DELETE";

    // Http Response Codes
    public static final int RESPONSE_METHOD_NOT_ALLOWED = 405;
    public static final int RESPONSE_OK = 200;
    public static final int RESPONSE_BAD_REQUEST = 400;

    // HTTP Request Headers
    public static final String HEADER_JSON = "application/json";

    // URL paths
    public static final String PATH_ROOT = "/";
    public static final String PATH_REPORTS = "/reports";



}
