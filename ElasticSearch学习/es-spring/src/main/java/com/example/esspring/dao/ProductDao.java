package com.example.esspring.dao;

import com.example.esspring.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/8/20
 **/
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
