package com.michaelakamihe.ecommercebackend.repo;

import com.michaelakamihe.ecommercebackend.model.cart.Commande;
import com.michaelakamihe.ecommercebackend.model.cart.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends JpaRepository <Commande, CartItemPK> {
}
