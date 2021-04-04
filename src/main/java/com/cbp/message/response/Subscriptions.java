package com.cbp.message.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.cbp.constants.Fields.ResponseType;

public class Subscriptions {

    private ResponseType type;

    private Channels[] channels;

    public Subscriptions() {
    }

    public ResponseType getType() {
        return type;
    }

    public Channels[] getChannels() {
        return channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Subscriptions that = (Subscriptions) o;

        return new EqualsBuilder()
                .append(getType(), that.getType())
                .append(getChannels(), that.getChannels())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getChannels())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("channels", channels)
                .toString();
    }
}