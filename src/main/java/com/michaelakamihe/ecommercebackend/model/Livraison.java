package com.michaelakamihe.ecommercebackend.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.michaelakamihe.ecommercebackend.model.cart.Commande;

@Entity 
public class Livraison {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;

	    @Column(nullable = false, length = 128)
	    private String ModeLivraison;

	    @Column(nullable = false, length = 4000)
	    private Date dateLivraison;
	    
	    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER)
	    private Commande commande;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getModeLivraison() {
			return ModeLivraison;
		}

		public void setModeLivraison(String modeLivraison) {
			ModeLivraison = modeLivraison;
		}

		public Date getDateLivraison() {
			return dateLivraison;
		}

		public void setDateLivraison(Date dateLivraison) {
			this.dateLivraison = dateLivraison;
		}

		public Commande getCommande() {
			return commande;
		}

		public void setCommande(Commande commande) {
			this.commande = commande;
		}
	    
	    
}
