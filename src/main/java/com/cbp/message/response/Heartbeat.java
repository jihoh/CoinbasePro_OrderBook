package com.cbp.message.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

import static com.cbp.constants.Fields.ResponseType;

public class Heartbeat {

    private ResponseType type;

    private long sequence;

    private long last_trade_id;

    private String product_id;

    private Date time;

    public Heartbeat() {
    }

    public ResponseType getType() {
        return type;
    }

    public long getSequence() {
        return sequence;
    }

    public long getLast_trade_id() {
        return last_trade_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Heartbeat heartbeat = (Heartbeat) o;

        return new EqualsBuilder()
                .append(getSequence(), heartbeat.getSequence())
                .append(getLast_trade_id(), heartbeat.getLast_trade_id())
                .append(getType(), heartbeat.getType())
                .append(getProduct_id(), heartbeat.getProduct_id())
                .append(getTime(), heartbeat.getTime())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getSequence())
                .append(getLast_trade_id())
                .append(getProduct_id())
                .append(getTime())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("sequence", sequence)
                .append("last_trade_id", last_trade_id)
                .append("product_id", product_id)
                .append("time", time)
                .toString();
    }
}
