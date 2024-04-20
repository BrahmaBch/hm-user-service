package com.hm.userservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name ="employee", schema ="demo")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empId;
	private String employeeOfficeId;
	private String employeeName;
	private String designation;
	private Integer experiance;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="company_id", referencedColumnName = "company_id")
	private Company company;
}
