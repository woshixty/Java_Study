package com.atguigu.spring5.service;

import com.atguigu.spring5.dao.BookDao;
import com.atguigu.spring5.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/12
 **/
@Service
public class BookService {
    //注入BookDao
    @Autowired
    private BookDao bookDao;
    public void addBook(Book book) {
        bookDao.add(book);
    }
}
