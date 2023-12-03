module com.restready.common {

    requires java.net.http;
    requires lombok;

    exports com.restready.common;
    exports com.restready.common.http;
    exports com.restready.common.util;
}