package ru.ndg.market.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.market.model.Product;
import ru.ndg.market.repository.ProductRepository;
import ru.ndg.market.service.product.spec.FilterProduct;

import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Map<String, String> params, Integer page) {
        if (page == null || page < 0) page = 0;
        else page -= 1;
        return productRepository.findAll(new FilterProduct(params).getSpecification(), PageRequest.of(page, 5));
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        // TODO: 30.11.2020 Доделать перехват исключений
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    @Transactional
    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }
}
