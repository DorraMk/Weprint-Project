package com.michaelakamihe.ecommercebackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.michaelakamihe.ecommercebackend.exceptions.LivraisonNotFoundException;
import com.michaelakamihe.ecommercebackend.exceptions.ProductNotFoundException;
import com.michaelakamihe.ecommercebackend.model.Livraison;
import com.michaelakamihe.ecommercebackend.model.Product;
import com.michaelakamihe.ecommercebackend.repo.LivraisonRepository;


@Service
public class LivraisonService {
	 private final LivraisonRepository repos;

	public LivraisonService(LivraisonRepository repos) {
	
		this.repos = repos;
	}
    public List<Livraison> getLivs () {
        return repos.findAll();
    }

    public Livraison getLiv (Long id) {
        return repos.findById(id).orElseThrow(() ->
                new LivraisonNotFoundException("Delivery by id " + id + " was not found."));
    
    }
	  public Livraison addLiv (Livraison liv) {
	        return repos.save(liv);
	    }
	  
	  public Livraison updateProduct (Long id, Livraison liv) {
	        Livraison oldLiv = getLiv(id);

	        oldLiv.setModeLivraison(liv.getModeLivraison());
	    

	        return repos.save(oldLiv);
	    }

	    public void deleteProduct (Long id) {
	        repos.deleteById(id);
	    }
	}

