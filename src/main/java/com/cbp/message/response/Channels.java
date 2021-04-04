package com.cbp.message.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

class Channels {

    private String name;

    private String[] product_ids;

    public Channels() {
    }

    public String getName() {
        return name;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Channels channels = (Channels) o;

        return new EqualsBuilder()
                .append(getName(), channels.getName())
                .append(getProduct_ids(), channels.getProduct_ids())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getName())
                .append(getProduct_ids())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("product_ids", product_ids)
                .toString();
    }
}
