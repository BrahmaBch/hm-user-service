package com.hm.userservice.mongoconfig.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.hm.userservice.mongoconfig.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String>{

	@Query("{ bookId : ?0 }")
	public Optional<Book> findBookById(Integer bookId);
	
    //@Query("{pages : {$lt: ?0}}")                                 // SQL Equivalent : SELECT * FROM BOOK where pages<?
    @Query("{ pages : { $gte: ?0 } }")                        // SQL Equivalent : SELECT * FROM BOOK where pages>=500
    //@Query("{ pages : ?0 }")                                      // SQL Equivalent : SELECT * FROM BOOK where pages=?
    List<Book> getBooksByPages(Integer pages);
}
