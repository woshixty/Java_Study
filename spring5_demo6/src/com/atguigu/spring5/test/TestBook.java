package com.atguigu.spring5.test;

import com.atguigu.spring5.entity.Book;
import com.atguigu.spring5.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/12
 **/
public class TestBook {
    @Test
    public void testAdd() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Book book = new Book();
        book.setUserId(1);
        book.setUsername("Java");
        book.setUstatus("sale");
        bookService.addBook(book);
    }
}
