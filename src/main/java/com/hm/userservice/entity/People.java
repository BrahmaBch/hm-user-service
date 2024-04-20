package com.hm.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name ="people", schema ="demo")
public class People {

	@Id
	private Integer peopleId;
    private String name;
    private Long accountId;
    private Long cardNumber;
    private String location;
    private String loanEligibity;
    private Integer loanAmmount;
    private Integer creditCardEligibility;

}
