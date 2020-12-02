package ru.ndg.market.service.product.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.ndg.market.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public class FilterProduct {

    private Specification<Product> spec;
    private Map<String, String> params;

    public FilterProduct(Map<String, String> params) {
        this.params = params;
        this.spec = Specification.where(null);
    }

    public Specification<Product> getSpecification() {
        if (params == null) return spec;

        StringBuilder filterOut = new StringBuilder("");

        String titlePart = params.get("title_part");
        if (titlePart != null && !titlePart.isEmpty()) {
            spec = spec.and((Specification<Product>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart)));
            filterOut.append("&title_part=").append(titlePart);
        }
        String minPriceParam = params.get("min_price");
        if (minPriceParam != null && !minPriceParam.isEmpty()) {
            try {
                Double minPrice = Double.parseDouble(minPriceParam);
                spec = spec.and((Specification<Product>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
                filterOut.append("&min_price=").append(minPrice);
            } catch (NumberFormatException ignore) {
            }
        }
        String maxPriceParam = params.get("max_price");
        if (maxPriceParam != null && !maxPriceParam.isEmpty()) {
            try {
                Double maxPrice = Double.parseDouble(maxPriceParam);
                spec = spec.and((Specification<Product>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
                filterOut.append("&max_price=").append(maxPrice);
            } catch (NumberFormatException ignore) {
            }
        }

        params.put("filterOut", filterOut.toString());

        return spec;
    }
}
