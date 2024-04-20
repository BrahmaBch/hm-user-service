package com.hm.userservice.mongoconfig.model;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("book-table") // Maps Entity class objects to JSON formatted Documents
public class Book {

	@Id
	private String id;

	@Field("name")
	private String name;
	@Field("pages")
	private Integer pages;
	@Field("cost")
	private Double cost;
	private AutherJson author;
	private List<PublisherJson> publishers;
	//@Field("bookId")
	private Integer bookId;
	
}
