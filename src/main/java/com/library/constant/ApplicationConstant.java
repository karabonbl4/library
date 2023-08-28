package com.library.constant;


import java.time.format.DateTimeFormatter;

public interface ApplicationConstant {

    String AUTHOR_IS_DELETED = "Author is deleted successfully!";

    String BOOK_IS_DELETED = "Book is deleted successfully!";

    String PUBLISHER_IS_DELETED = "Publisher is deleted successfully!";

    String ENTITY_NOT_FOUND = "Entity not found!";

    String FIELD_NOT_PRESENT = "Required field is not present!";

    String AUTHOR_CANT_BE_DELETED = "The author can't be deleted if he has books!";

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
}