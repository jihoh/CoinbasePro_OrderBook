package com.cbp.orderbook;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class OrderBookLevel implements Comparable<OrderBookLevel> {
    private final String price;
    private String size;

    public OrderBookLevel(final String price, final String size) {
        this.price = price;
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPriceInDouble() {
        return Double.parseDouble(price);
    }

    public double getSizeInDouble() {
        return Double.parseDouble(size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OrderBookLevel that = (OrderBookLevel) o;

        return new EqualsBuilder()
                .append(getPrice(), that.getPrice())
                .append(getSize(), that.getSize())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPrice())
                .append(getSize())
                .toHashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", price, size);
    }

    @Override
    public int compareTo(OrderBookLevel other) {
        return Double.compare(this.getPriceInDouble(), other.getPriceInDouble());
    }
}
