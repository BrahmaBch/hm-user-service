package com.hm.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses", schema = "demo")
public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="address_id")
    Long addressId;
    String street;
    String city;
   // @JsonIgnore
    //@OneToOne(mappedBy = "addresses")
    //PGPerson pgPerson;
}
