package com.michaelakamihe.ecommercebackend.service;

import com.michaelakamihe.ecommercebackend.exceptions.CartItemAlreadyExistsException;
import com.michaelakamihe.ecommercebackend.exceptions.CartItemDoesNotExistsException;
import com.michaelakamihe.ecommercebackend.model.cart.Commande;
import com.michaelakamihe.ecommercebackend.model.cart.CartItemPK;
import com.michaelakamihe.ecommercebackend.repo.CartItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartItemService {
    private CartItemRepository repo;

    public CartItemService (CartItemRepository repo) {
        this.repo = repo;
    }

    public List<Commande> getCartItems () {
        return repo.findAll();
    }

    public Commande getCartItem (Long userId, Long productId) {
        for (Commande item : getCartItems()) {
            if (item.getPk().getUser().getId() == userId && item.getPk().getProduct().getId() == productId) {
                return item;
            }
        }

        throw new CartItemDoesNotExistsException(
                "Cart item w/ user id " + userId + " and product id " + productId + " does not exist."
        );
    }

    public Commande addCartItem(Commande cartItem) {
        for (Commande item : getCartItems()) {
            if (item.equals(cartItem)) {
                throw new CartItemAlreadyExistsException(
                        "Cart item w/ user id " + cartItem.getPk().getUser().getId() + " and product id " +
                        cartItem.getProduct().getId() + " already exists."
                );
            }
        }

        return this.repo.save(cartItem);
    }

    public Commande updateCartItem(Commande cartItem) {
        for (Commande item : getCartItems()) {
            if (item.equals(cartItem)) {
                item.setQuantity(cartItem.getQuantity());
                return repo.save(item);
            }
        }

        throw new CartItemDoesNotExistsException(
                "Cart item w/ user id " + cartItem.getPk().getUser().getId() + " and product id " +
                        cartItem.getProduct().getId() + " does not exist."
        );
    }
    
    
    
    
    
    
    public Commande approveCommande(Commande cartItem) {
        for (Commande item : getCartItems()) {
            if (item.equals(cartItem)&&(item.getState()==false)) {
                item.setState(true);
                return repo.save(item);
            }
        }

        throw new CartItemDoesNotExistsException(
                "Cart item w/ user id " + cartItem.getPk().getUser().getId() + " and product id " +
                        cartItem.getProduct().getId() + " does not exist."
        );
    }

    public void deleteCartItem (Long userId, Long productId) {
        for (Commande item : getCartItems()) {
            if (item.getPk().getUser().getId() == userId && item.getPk().getProduct().getId() == productId) {
                repo.delete(item);
                return;
            }
        }

        throw new CartItemDoesNotExistsException(
                "Cart item w/ user id " + userId + " and product id " + productId + " does not exist."
        );
    }
}
