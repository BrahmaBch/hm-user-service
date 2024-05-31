package com.hm.userservice.mongoconfig.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection =  "ViewEligibility")
public class ViewEligibility {

	@Id
    private String id;
	
	@Indexed
	private Integer viewEligibilityId;
    private String name;
    private Integer loanAmount;
    private String loanEligibility;
    private String location;
}
