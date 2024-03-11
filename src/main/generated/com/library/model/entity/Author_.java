package com.library.model.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Author.class)
public abstract class Author_ extends com.library.model.entity.ParentEntity_ {

	public static volatile ListAttribute<Author, Book> books;
	public static volatile SingularAttribute<Author, String> surname;
	public static volatile SingularAttribute<Author, String> name;
	public static volatile SingularAttribute<Author, LocalDate> deathDate;
	public static volatile SingularAttribute<Author, LocalDate> birthDate;

	public static final String BOOKS = "books";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String DEATH_DATE = "deathDate";
	public static final String BIRTH_DATE = "birthDate";

}

