package com.hm.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="product", schema = "demo")
@Setter
@Getter
@EqualsAndHashCode
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long producId;

	@Column(name = "cost")
	private Integer cost;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "status")
	private String status;

}
