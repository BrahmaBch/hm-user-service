package com.hm.userservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//@Data
@Entity
@Table(name="pg_person", schema = "demo")
@Setter
@Getter
@EqualsAndHashCode
public class PGPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Long id;

	@Column(name = "age")
	private Integer age;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	/*
	 * performing oneToOne unidirectional mapping 
	 * the pg_person table has a foreign key column address_id that refers to the primary key column id of the addresses table. 
	 *  there’s no reference to PGPerson in the Address class, thus the relationship is unidirectional.
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Address address;
}
