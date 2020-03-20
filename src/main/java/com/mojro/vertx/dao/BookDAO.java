package com.mojro.vertx.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.mojro.vertx.entity.Book;


public class BookDAO {
	


	private static final Map<String, Book> bookMap = new HashMap<String, Book>();

	static {
		initBooks();
	}

	private static void initBooks() {
		Book book1 = new Book("A101", "Java", "John", 598.25);
		Book book2 = new Book("A102", "Python", "Rahul", 899.95);
		Book book3 = new Book("A103", "JavaScript", "Venki", 5898.60);
		bookMap.put(book1.getISBN(), book1);
		bookMap.put(book2.getISBN(), book2);
		bookMap.put(book3.getISBN(), book3);
	}

	public Book getBook(String ISBN) {
		return bookMap.get(ISBN);
	}

	public Book addBook(Book book) {
		bookMap.put(book.getISBN(), book);
		return book;
	}

	public Book updateBook(Book book) {
		bookMap.put(book.getISBN(), book);
		return book;
	}

	public void deleteBook(String ISBN) {
		bookMap.remove(ISBN);
	}

	public List<Book> getAllBooks() {
		Collection<Book> c = bookMap.values();
		List<Book> list = new ArrayList<Book>();
		list.addAll(c);
		return list;
	}

}
