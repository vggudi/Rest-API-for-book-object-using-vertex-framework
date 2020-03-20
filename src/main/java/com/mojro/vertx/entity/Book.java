package com.mojro.vertx.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "book")
public class Book  {
	private String ISBN;
	private String name;
	private String author;
	private double price;

	public Book() {

	}

	public Book(String ISBN, String name, String author, double price) {

		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.price = price;
	}

	@Id
	@Column(name = "ISBN", nullable = false)
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "author", nullable = false)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "price", nullable = false)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	@Override
    public String toString() {
        return "{" +
                "ISBN=" + ISBN +
                ", name='" + name + '\'' +
                ", Author='" + author + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

}
