package com.cbp.message.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Subscribe {

    final private String type = "subscribe";

    private final String[] product_ids;

    private final String[] channels;

    public Subscribe(@Value("${subscribe.productId}") String[] product_ids,
                     @Value("${subscribe.channels}") String[] channels) {
        this.product_ids = product_ids;
        this.channels = channels;
    }

    public String getType() {
        return type;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    public String[] getChannels() {
        return channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Subscribe subscribe = (Subscribe) o;

        return new EqualsBuilder()
                .append(getType(), subscribe.getType())
                .append(getProduct_ids(), subscribe.getProduct_ids())
                .append(getChannels(), subscribe.getChannels())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getProduct_ids())
                .append(getChannels())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("product_ids", product_ids)
                .append("channels", channels)
                .toString();
    }
}
