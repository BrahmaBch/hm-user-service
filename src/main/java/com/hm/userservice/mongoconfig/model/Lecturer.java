package com.hm.userservice.mongoconfig.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("book-table")
public class Lecturer {
	
	@Id
	private String id;

	@Field("lecturer_name")
	private String lecturerName;
	@Field("salary")
	private Double salary;
	@Field("subject")
	private String subject;
	@Field("experience")
	private Integer experience;

}
