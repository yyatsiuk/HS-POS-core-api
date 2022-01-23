package com.yyatsiuk.api.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DeliveryInformation {

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "branch_number", nullable = false)
    private Integer branchNumber;

    @Column(name = "tracking_code")
    private String trackingCode;

}
