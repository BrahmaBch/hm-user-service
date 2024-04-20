package com.hm.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eligibility", schema = "demo")
public class Eligibility {

	@Id
	private Integer eligibilityId;
	private String location;
	private String loanEligibity;
	private Integer loanAmmount;
}
