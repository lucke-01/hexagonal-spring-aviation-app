package com.ricardocreates.infra.data.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(schema = "SMART", name = "SHIPMENT")
public class ShipmentEntity extends LoadEntity {
}
