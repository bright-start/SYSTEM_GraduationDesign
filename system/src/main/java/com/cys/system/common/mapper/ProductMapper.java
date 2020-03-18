package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Product;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper {
    void addProduct(Product product);

    void updateProduct(Product product);

    void deleteProductByGoodsId(Integer id);

    Product findProductInfoById(Integer productId);
}
