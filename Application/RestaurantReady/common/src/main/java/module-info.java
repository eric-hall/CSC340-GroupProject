module com.restready.common {

    requires java.net.http;
    requires lombok;
    requires java.desktop;

    exports com.restready.common;
    exports com.restready.common.http;
    exports com.restready.common.util;
}