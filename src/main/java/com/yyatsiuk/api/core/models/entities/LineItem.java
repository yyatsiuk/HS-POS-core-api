package com.yyatsiuk.api.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "line_item")
public class LineItem {

    @EmbeddedId
    private LineItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "discount_amount", precision = 19, scale = 2)
    private BigDecimal discountAmount;

    public LineItem(Order order, Product product, Integer quantity, BigDecimal discountAmount) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.discountAmount = discountAmount;
        this.id = new LineItemId(order.getId(), product.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem that = (LineItem) o;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }

}
