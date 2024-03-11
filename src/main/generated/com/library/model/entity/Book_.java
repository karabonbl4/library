package com.library.model.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ extends com.library.model.entity.ParentEntity_ {

	public static volatile SingularAttribute<Book, String> inventoryNumber;
	public static volatile SingularAttribute<Book, Integer> stack;
	public static volatile SingularAttribute<Book, String> unit;
	public static volatile SingularAttribute<Book, Boolean> taken;
	public static volatile SingularAttribute<Book, Short> publicationYear;
	public static volatile SingularAttribute<Book, Publisher> publisher;
	public static volatile ListAttribute<Book, Book> referenceBooks;
	public static volatile SingularAttribute<Book, String> title;
	public static volatile ListAttribute<Book, Author> authors;

	public static final String INVENTORY_NUMBER = "inventoryNumber";
	public static final String STACK = "stack";
	public static final String UNIT = "unit";
	public static final String TAKEN = "taken";
	public static final String PUBLICATION_YEAR = "publicationYear";
	public static final String PUBLISHER = "publisher";
	public static final String REFERENCE_BOOKS = "referenceBooks";
	public static final String TITLE = "title";
	public static final String AUTHORS = "authors";

}

