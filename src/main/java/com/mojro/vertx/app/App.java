package com.mojro.vertx.app;

import com.mojro.vertx.dao.BookDAO;
import com.mojro.vertx.entity.Book;
import java.util.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.cli.converters.ValueOfBasedConverter;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class App {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		// Creating Http Server using Vertx
		HttpServer httpsServer = vertx.createHttpServer();
		Router router = Router.router(vertx);

		BookDAO bookDao = new BookDAO();

		// -- GET method for getting List of Books
		// "Path" /books
		router.get("/books").handler(BodyHandler.create()).handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			response.end("List of Books : " + bookDao.getAllBooks().toString());
		});

		// -- POST method for creating an Book
		// "path" /book
		router.post("/book").produces("application/json").handler(BodyHandler.create()).handler(routingContext -> {
			String ISBN = routingContext.request().getParam("ISBN");
			String name = routingContext.request().getParam("name");
			String author = routingContext.request().getParam("author");
			String stringPrice = routingContext.request().getParam("price");
			double price=Double.parseDouble(stringPrice);
		
			Book book = new Book(ISBN, name, author, price);

			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			bookDao.addBook(book);
			response.putHeader("content-type", "text/plain");
			response.setStatusCode(201).end("new Book added: data after addition " + bookDao.getAllBooks().toString());

		});

		// Get Method for getting Book by ISBN
		// "path" /book/ISBN
		router.get("/book/:ISBN").handler(BodyHandler.create()).handler(routingContext -> {
			String ISBN = routingContext.request().getParam("ISBN");
			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			response.end(" Book  : " + bookDao.getBook(ISBN).toString());

		});

		// Update Book using ISBN
		// "Path" /book/ISBN
		router.put("/book/:ISBN").handler(BodyHandler.create()).handler(routingContext ->

		{
			String ISBN = routingContext.request().getParam("ISBN");
			if (ISBN != null) {
				for (Book book : bookDao.getAllBooks()) {
					if (book.getISBN().equals(ISBN)) {
						String name = routingContext.request().getParam("name");
						String author = routingContext.request().getParam("author");
						String price = routingContext.request().getParam("price");

						// using Entity Builder For creating Object
						
						Book bookUpdate = new Book(ISBN,name,author,Double.parseDouble(price));
						HttpServerResponse response = routingContext.response();
						response.setChunked(true);
						bookDao.deleteBook(ISBN);
						bookDao.addBook(bookUpdate);
						response.putHeader("content-type", "text/plain");
						response.end("Data Updated : data after updating " + bookDao.getAllBooks());
					}

				}
			}

		});

		// Delete a Book Given ISBN
		// "Path" /book/ISBN
		router.delete("/book/:ISBN").handler(BodyHandler.create()).handler(routingContext -> {
			String ISBN = routingContext.request().getParam("ISBN");

			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			bookDao.deleteBook(ISBN);
			response.putHeader("content-type", "text/plain");
			response.setStatusCode(204).end("Data Deleted : data after deletion " + bookDao.getAllBooks().toString());

		});

		// Running Vertx Server with Port 8080
		httpsServer.requestHandler(router::accept).listen(8080);
	}
}
