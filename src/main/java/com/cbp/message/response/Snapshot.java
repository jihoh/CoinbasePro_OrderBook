package com.cbp.message.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.cbp.constants.Fields.ResponseType;

public class Snapshot {

    private ResponseType type;

    private String product_id;

    private String[][] bids;

    private String[][] asks;

    public Snapshot() {
    }

    public ResponseType getType() {
        return type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String[][] getBids() {
        return bids;
    }

    public String[][] getAsks() {
        return asks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Snapshot snapshot = (Snapshot) o;

        return new EqualsBuilder()
                .append(getType(), snapshot.getType())
                .append(getProduct_id(), snapshot.getProduct_id())
                .append(getBids(), snapshot.getBids())
                .append(getAsks(), snapshot.getAsks())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getProduct_id())
                .append(getBids())
                .append(getAsks())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("product_id", product_id)
                .append("bids", bids)
                .append("asks", asks)
                .toString();
    }
}
