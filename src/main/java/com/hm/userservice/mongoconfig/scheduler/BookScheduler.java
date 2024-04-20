package com.hm.userservice.mongoconfig.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hm.userservice.mongoconfig.model.BookMetaData;
import com.hm.userservice.mongoconfig.service.BookService;

@Component
public class BookScheduler {

	@Autowired
	BookService bookService;
	
	//@Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void getBookDetails() {
		try {
			System.out.println("book scheduler:::::::::::::::::::::::::::::::::::::start");
		BookMetaData book= bookService.getBookByCost(520.0);
		System.out.println(book.getAuthor());
		System.out.println("book scheduler:::::::::::::::::::::::::::::::::::::end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
