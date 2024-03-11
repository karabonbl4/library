package com.library.model.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ParentEntity.class)
public abstract class ParentEntity_ {

	public static volatile SingularAttribute<ParentEntity, Boolean> deleted;
	public static volatile SingularAttribute<ParentEntity, Long> id;

	public static final String DELETED = "deleted";
	public static final String ID = "id";

}

