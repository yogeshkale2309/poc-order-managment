package org.ordermanagement.ordermanagement.serviceimpl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.ordermanagement.ordermanagement.dto.ProductDTO;
import org.ordermanagement.ordermanagement.entity.QCustomer;
import org.ordermanagement.ordermanagement.entity.QProduct;
import org.ordermanagement.ordermanagement.entity.QShoppingCart;
import org.ordermanagement.ordermanagement.service.ProductRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    QCustomer qCustomer = QCustomer.customer;
    QProduct qProduct = QProduct.product;
    QShoppingCart qShoppingCart = QShoppingCart.shoppingCart;


    @Override
    public List<ProductDTO> findWithShoppingCarts(Long customerId) {
        JPAQuery<ProductDTO> query = new JPAQuery<>(entityManager);

        return query.select(Projections.bean(ProductDTO.class,
                        qProduct.id,
                        qProduct.name,
                        qProduct.price
                ))
                .from(qProduct)
                .join(qProduct.shoppingCarts, qShoppingCart)
                .join(qShoppingCart.customer, qCustomer)
                .where(qCustomer.id.eq(customerId))
                .fetch();
    }

}
