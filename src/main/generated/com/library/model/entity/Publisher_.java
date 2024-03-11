package com.library.model.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Publisher.class)
public abstract class Publisher_ extends com.library.model.entity.ParentEntity_ {

	public static volatile SingularAttribute<Publisher, String> country;
	public static volatile ListAttribute<Publisher, Book> books;
	public static volatile SingularAttribute<Publisher, String> city;
	public static volatile SingularAttribute<Publisher, String> street;
	public static volatile SingularAttribute<Publisher, String> buildingNumber;
	public static volatile SingularAttribute<Publisher, String> title;

	public static final String COUNTRY = "country";
	public static final String BOOKS = "books";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String BUILDING_NUMBER = "buildingNumber";
	public static final String TITLE = "title";

}

