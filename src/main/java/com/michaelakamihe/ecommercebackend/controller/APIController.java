package com.michaelakamihe.ecommercebackend.controller;

import com.michaelakamihe.ecommercebackend.config.JwtUtil;
import com.michaelakamihe.ecommercebackend.model.Livraison;
import com.michaelakamihe.ecommercebackend.model.Product;
import com.michaelakamihe.ecommercebackend.model.User;
import com.michaelakamihe.ecommercebackend.model.cart.Commande;
import com.michaelakamihe.ecommercebackend.repo.ProductRepository;
import com.michaelakamihe.ecommercebackend.model.cart.CartItemPK;
import com.michaelakamihe.ecommercebackend.service.CartItemService;
import com.michaelakamihe.ecommercebackend.service.JwtUserDetailsService;
import com.michaelakamihe.ecommercebackend.service.LivraisonService;
import com.michaelakamihe.ecommercebackend.service.ProductService;
import com.michaelakamihe.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class APIController {
    private final UserService userService;
    private final ProductService productService;
    private final LivraisonService LivraisonService;
    private final CartItemService cartItemService;
    
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    private  ProductRepository productrep;
    
    
    public APIController(UserService userService, ProductService productService, CartItemService cartItemService,LivraisonService livraisonService) {
        this.userService = userService;
        this.productService = productService;
		this.LivraisonService = livraisonService;
        this.cartItemService = cartItemService;
    }

    @PostMapping("/create-token")
    public ResponseEntity<?> createToken (@RequestBody Map<String, String> user) throws Exception {
        Map<String, Object> tokenResponse = new HashMap<>();
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.get("username"));
        final String token = jwtUtil.generateToken(userDetails);

        tokenResponse.put("token", token);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers () {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUser (@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> updateUser (@PathVariable("id") Long id, @RequestBody Map<String, Object> user) {
        User newUser = new User(
                (String) user.get("username"),
                (String) user.get("password"),
                (String) user.get("email"),
                (String) user.get("name"),
                (String) user.get("address"),
                (String) user.get("phone")
        );

        return new ResponseEntity<>(userService.updateUser(id, newUser), HttpStatus.OK);
    }

    @GetMapping("/users/{id}/cart")
    public ResponseEntity<List<Commande>> getUserCart (@PathVariable("id") Long id) {
        System.out.println(userService.getUser(id).getCartItems().size());
        return new ResponseEntity<>(userService.getUser(id).getCartItems(), HttpStatus.OK);
    }

    @PostMapping("/users/{id}/cart/add/{productId}")
    public ResponseEntity<User> addToUserCart (@PathVariable("id") Long id,
                                               @PathVariable("productId") Long productId) {
        User user = userService.getUser(id);
        Product product = productService.getProduct(productId);

        Commande cartItem = new Commande(user, product, 1,false);
        cartItemService.addCartItem(cartItem);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}/cart/update/{productId}")
    public ResponseEntity<User> updateCartItem (@PathVariable("id") Long id,
                                                @PathVariable("productId") Long productId,
                                                @RequestBody Commande cartItem) {
        User user = userService.getUser(id);
        Product product = productService.getProduct(productId);

        cartItem.setPk(new CartItemPK(user, product));
        cartItemService.updateCartItem(cartItem);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}/cart/remove/{productId}")
    public ResponseEntity<User> removeFromUserCart (@PathVariable("id") Long id,
                                                    @PathVariable("productId") Long productId) {
        cartItemService.deleteCartItem(id, productId);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts () {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct (@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }
    
    
    /////////////////////////////
    
    
  /*  @PostMapping("/upload")
	public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		Product img = new Product(compressBytes(file.getBytes()));
		productrep.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}
    */
    
 // compress the image bytes before storing it in the database
 	public static byte[] compressBytes(byte[] data) {
 		Deflater deflater = new Deflater();
 		deflater.setInput(data);
 		deflater.finish();
 		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
 		byte[] buffer = new byte[1024];
 		while (!deflater.finished()) {
 			int count = deflater.deflate(buffer);
 			outputStream.write(buffer, 0, count);
 		}
 		try {
 			outputStream.close();
 		} catch (IOException e) {
 		}
 		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
 		return outputStream.toByteArray();
 	}
    
    
    
 ///////////////////////////////////   
    

    @PostMapping("/product")
    public Product addProduct (@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable("id") Long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<Commande>> getCartItems () {
        return ResponseEntity.ok(cartItemService.getCartItems());
    }

    @CrossOrigin
    @GetMapping("/cart-items/{id}/{productId}")
    public ResponseEntity<Commande> getCartItem (@PathVariable("id") Long id,
                                                 @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(cartItemService.getCartItem(id, productId));
    }
    
    
    
    
    
    

    @PutMapping("/users/{id}/cart/approve/{productId}")
    public ResponseEntity<User> approveCommande (@PathVariable("id") Long id,
                                                @PathVariable("productId") Long productId,
                                                @RequestBody Commande cartItem) {
        User user = userService.getUser(id);
        Product product = productService.getProduct(productId);

        cartItem.setPk(new CartItemPK(user, product));
        cartItemService.approveCommande(cartItem);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
    
    
    
    
    
    
    
    
    
    @GetMapping("/all")
	public ResponseEntity<?> getContent() {
		return ResponseEntity.ok("Public content goes here");
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserContent() {
		return ResponseEntity.ok("User content goes here");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAdminContent() {
		return ResponseEntity.ok("Admin content goes here");
	}
	
	
	
	
	
	
	  @GetMapping("/delivery")
	    public ResponseEntity<List<Livraison>> getdeliveries () {
	        return new ResponseEntity<>(LivraisonService.getLivs(), HttpStatus.OK);
	    }

	    @GetMapping("/delivery/{id}")
	    public ResponseEntity<Livraison> getDelivery (@PathVariable("id") Long id) {
	        return new ResponseEntity<>(LivraisonService.getLiv(id), HttpStatus.OK);
	    }

	    @PostMapping("delivery/addLiv")
	    public ResponseEntity<Livraison> addDelivery (@RequestBody Livraison liv) {
	        return new ResponseEntity<>(LivraisonService.addLiv(liv), HttpStatus.OK);
	    }


	
	
}
