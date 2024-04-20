package com.hm.userservice.mongoconfig.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookMetaData {
	@Field("name")
	private String name;
	@Field("pages")
	private Integer pages;
	@Field("cost")
	private Double cost;
	private AutherJson author;
	private List<PublisherJson> publishers;
	@Field("bookId")
	private Integer bookId;
	@Override
	public String toString() {
		return "BookMetaData [name=" + name + ", pages=" + pages + ", cost=" + cost + ", author=" + author
				+ ", publishers=" + publishers + ", bookId=" + bookId + "]";
	}
	

}
