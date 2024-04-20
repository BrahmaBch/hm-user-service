package com.hm.userservice.mongoconfig.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hm.userservice.mongoconfig.dao.BookRepository;
import com.hm.userservice.mongoconfig.model.Book;
import com.hm.userservice.mongoconfig.model.BookMetaData;
import com.hm.userservice.mongoconfig.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Optional<Book> getBook(Integer bookId) {
	    if (bookId != null) {
	        try {
	            return bookRepository.findBookById(bookId);
	        } catch (Exception e) {
	            // Log or handle the exception appropriately
	            e.printStackTrace();
	            return Optional.empty(); // Return an empty Optional in case of exception
	        }
	    } else {
	        return Optional.empty(); // Return an empty Optional if bookId is null
	    }
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public void deleteBook(Integer bookId) {
		System.out.println("delete book by id:::::::::::::::::");
		//bookRepository.deleteById(bookId);
	}

	@Override
	public void deleteAllBooks() {
		System.out.println("delete all books :::::::::::::::::");
		bookRepository.deleteAll();		
	}

	@Override
	public List<Book> getBooksByPages(Integer pages) {
		return bookRepository.getBooksByPages(pages);
	}

	@Override
	public BookMetaData getBookByCost(Double cost) throws Exception{
		
		Query query = new Query();
		query.addCriteria(Criteria.where("cost").is(cost));
		
		BookMetaData book = mongoTemplate.findOne(query, BookMetaData.class, "book-table");
		return book;
	}

}
