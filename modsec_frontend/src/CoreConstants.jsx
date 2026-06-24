class coreConstants {
    static CHECK_MARK_IMG_PATH = "/src/assets/check-mark.svg";
    static CROSS_MARK_IMG_PATH = "/src/assets/cross-mark.svg";
    static SERVER_URL = "http://localhost:8080/";
    static SERVER_WS_URL = "ws://localhost:8080/";

    static NEW_REPORTS_URL = coreConstants.SERVER_WS_URL + "ws-reports";
    static ALL_REPORTS_URL = coreConstants.SERVER_URL + "reports";
    static ARMING_URL = coreConstants.SERVER_URL + "arm-toggle";

}

export default coreConstants;