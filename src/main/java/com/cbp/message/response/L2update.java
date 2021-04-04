package com.cbp.message.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

import static com.cbp.constants.Fields.ResponseType;

public class L2update {

    private ResponseType type;

    private String product_id;

    private Date time;

    private String[][] changes;

    public L2update() {
    }

    public ResponseType getType() {
        return type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public Date getTime() {
        return time;
    }

    public String[][] getChanges() {
        return changes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        L2update l2update = (L2update) o;

        return new EqualsBuilder()
                .append(getType(), l2update.getType())
                .append(getProduct_id(), l2update.getProduct_id())
                .append(getTime(), l2update.getTime())
                .append(getChanges(), l2update.getChanges())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getProduct_id())
                .append(getTime())
                .append(getChanges())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("product_id", product_id)
                .append("time", time)
                .append("changes", changes)
                .toString();
    }
}
