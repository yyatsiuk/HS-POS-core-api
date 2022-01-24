package com.yyatsiuk.api.core.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class LineItemId implements Serializable {

    @Serial
    private static final long serialVersionUID = -6991918723761486338L;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    public LineItemId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public LineItemId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItemId that = (LineItemId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

}
