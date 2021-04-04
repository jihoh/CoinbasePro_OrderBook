package com.cbp.message.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Unsubscribe {

    final private String type = "unsubscribe";

    private final String[] channels;

    public Unsubscribe(@Value("${subscribe.channels}") String[] channels) {
        this.channels = channels;
    }

    public String getType() {
        return type;
    }

    public String[] getChannels() {
        return channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Unsubscribe that = (Unsubscribe) o;

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
