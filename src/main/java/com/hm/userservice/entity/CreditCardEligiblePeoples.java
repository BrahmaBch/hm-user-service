package com.hm.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card_eligibility_people", schema = "demo")
public class CreditCardEligiblePeoples {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cardEligibilityPeopleId;
    private Long accountId;
    private Integer peopleId;
    private Integer creditCardEligibility;
    private String name;

}
