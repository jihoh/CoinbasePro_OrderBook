package com.cbp.constants;

public class Fields {
    public enum Type {
        type
    }

    public enum RequestType {
        subscribe, unsubscribe
    }

    public enum ResponseType {
        l2update, subscriptions, snapshot, heartbeat, error
    }

    public enum Side {
        buy, sell, ask, bid
    }
}