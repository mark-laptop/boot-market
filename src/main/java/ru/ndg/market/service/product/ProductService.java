package ru.ndg.market.service.product;

import org.springframework.data.domain.Page;
import ru.ndg.market.model.Product;

import java.util.Map;

public interface ProductService {

    Page<Product> getAllProducts(Map<String, String> params, Integer page);
    Product getProductById(Long id);
    Product saveOrUpdateProduct(Product product);
}
