package com.hm.userservice.mongoconfig.service;

import java.util.List;
import java.util.Optional;

import com.hm.userservice.mongoconfig.model.Book;
import com.hm.userservice.mongoconfig.model.BookMetaData;

public interface BookService {

	public Book saveBook(Book book);
    public Optional<Book> getBook(Integer bookId);
    public List<Book> getAllBooks();
    public void deleteBook(Integer bookId);
    public void deleteAllBooks();
    
    
    List<Book> getBooksByPages(Integer pages);
    
    // for MongoTemplate operations
    
    BookMetaData getBookByCost(Double cost) throws Exception;
    
}
