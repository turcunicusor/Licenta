package com.smarthome.server.hal.generic;

public class Protocol {
    public static final byte EXCEPTION = 101;
    public static final byte SUCCESS = 109;

    public static final byte OPEN = 102;
    public static final byte CLOSE = 103;

    public static final byte IS_OPENED = 104;
    public static final byte GET_TYPE = 105;
    public static final byte GET_PARAMS = 106;

    public static final byte COMMAND = 107;
    public static final byte QUERRY_DATA = 108;
}
