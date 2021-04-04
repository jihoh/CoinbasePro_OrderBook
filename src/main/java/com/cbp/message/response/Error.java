package com.cbp.message.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.cbp.constants.Fields.ResponseType;

public class Error {

    private ResponseType type;

    private String message;

    public Error() {
    }

    public ResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Error error = (Error) o;

        return new EqualsBuilder()
                .append(getType(), error.getType())
                .append(getMessage(), error.getMessage())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getMessage())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("message", message)
                .toString();
    }
}
