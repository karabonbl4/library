package com.library.constant;


import java.time.format.DateTimeFormatter;

public final class ApplicationConstant {

    public final static String AUTHOR_IS_DELETED = "Author is deleted successfully!";

    public final static String BOOK_IS_DELETED = "Book is deleted successfully!";

    public final static String PUBLISHER_IS_DELETED = "Publisher is deleted successfully!";

    public final static String ENTITY_NOT_FOUND = "Entity not found!";

    public final static String FIELD_NOT_PRESENT = "Required field is not present!";

    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
}