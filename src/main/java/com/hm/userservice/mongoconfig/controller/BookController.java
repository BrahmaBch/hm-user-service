package com.hm.userservice.mongoconfig.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.userservice.mongoconfig.dao.BookRepository;
import com.hm.userservice.mongoconfig.model.Book;
import com.hm.userservice.mongoconfig.service.BookService;

@RestController
@RequestMapping("/mongoconfig/api/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookRepository;

	@PostMapping("/save-book")
	public ResponseEntity<Book> saveBookInMongoDB(@RequestBody Book book) {

		try {
			bookService.saveBook(book);
			return new ResponseEntity<Book>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-all-books")
	public ResponseEntity<List<Book>> getAllBooks() {

		try {
			List<Book> books = new ArrayList<Book>();
			books = bookService.getAllBooks();
			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else
				//books = bookRepository.findAll();
				//books.forEach(books::add);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getBooksById/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable("bookId") Integer bookId) {

		Optional<Book> book = bookService.getBook(bookId);

		if (book.isPresent()) {
			return new ResponseEntity<>(book.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update-books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") Integer bookId, @RequestBody Book book) {

        Optional<Book> opt = bookService.getBook(bookId);

        if (opt.isPresent()) {
          Book book1 = opt.get();
          book1.setName(book.getName());
          book1.setCost(book.getCost());
          return new ResponseEntity<>(bookRepository.save(book1), HttpStatus.OK);
        } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@DeleteMapping("/delete-book/{bookId}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("bookId") Integer bookId) {

        try {
        	bookService.deleteBook(bookId);
        	System.out.println("delete book by id "+ bookId);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {

        try {
        	bookService.deleteAllBooks();
        	System.out.println("all books deleted::::::::::::");
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getBooksByPages/{pages}")
    public ResponseEntity<List<Book>> getBookByPages(Integer pages) {
    	List<Book> books = bookService.getBooksByPages(pages);

		if (!books.isEmpty()) {
			System.out.println(pages);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

}
