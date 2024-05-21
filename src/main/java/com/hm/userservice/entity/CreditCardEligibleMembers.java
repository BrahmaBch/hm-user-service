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
@Table(name = "credit_card_eligible_members", schema = "demo")
public class CreditCardEligibleMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long creditCardEligibleMembersId;
	
	private String name;
    private Long accountId;
    private Long cardNumber;
    private Integer loanAmmount;

}
