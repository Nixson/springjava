package com.example.sweater.controller;

import com.example.sweater.domain.*;
import com.example.sweater.repos.ProductRepository;
import com.example.sweater.repos.UserRepo;
import com.example.sweater.repos.shipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {

      @Autowired
      private ProductRepository productRepository;

      @Autowired
      private shipRepo ShipRepo;

    @Autowired
    private UserRepo userRepo;

      @GetMapping("/all")  
	  public String getAllProducts(Model model, Principal user,  RedirectAttributes redirectAttributes) {
          Set<Role> role = userRepo.findByUsername(user.getName()).getRoles();

 		//model.addAttribute("products",productService.getAllProducts());
		  Iterable<Product> products = productRepository.findAll();
		  model.addAttribute("products",products);
		  if (role.toArray()[0] == Role.USER) {
              model.addAttribute("role", "hidden");
          }else{
              model.addAttribute("role", "");
          }
		  return "product_all";
	  }

    @GetMapping("/add")
    public String getAddProducts(Model model) {
        //model.addAttribute("products",productService.getAllProducts());
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products",products);
        return "product_add";
    }

    @GetMapping("/shipping")
    public String shipList(Model model, Principal user) {

        List<Ship> shipList = ShipRepo.findAllByUser(userRepo.findByUsername(user.getName()).getId());
        List<Product> productList = new ArrayList<>();
        for (Ship ship : shipList){
            Optional<Product> products = productRepository.findById(ship.getProduct_id());
            products.ifPresent(productList::add);
        }
        model.addAttribute("shiplist", productList);
        return "shopping_list";
    }

    @PostMapping("/add")
    public String addUser(Product product, Map<String, Object> model) {

        productRepository.save(product);

     //   return "redirect:/products/all";
        return "redirect:all";
    }

    @PostMapping("/{productId}")
    public String removeProduct(@PathVariable("productId") Long productId) {

        productRepository.deleteById(productId);
        return "main";
    }
     
      @GetMapping("/{productId}")
      public String getProductById(Model model, @PathVariable("productId") Long productId) {
          //  Optional<Product> products = productRepository.findById(Long.valueOf(productId));
          Optional<Product> products = productRepository.findAllById(productId);
          ArrayList<Product> res = new ArrayList<>();
          products.ifPresent(res::add);
          //  if (products.isPresent()) {
          model.addAttribute("product", res);
          //   }
          return "product";
      }

    @GetMapping("/remove{productId}")
    public String removeProduct(Model model, Product product, @PathVariable("productId") Long productId) {
        productRepository.deleteById(productId);
        return "redirect:all";
    }

    @GetMapping("/edit{productId}")
    public String editProduct(Model model, @PathVariable("productId") Long productId) {
        Optional<Product> products = productRepository.findAllById(productId);
        ArrayList<Product> res = new ArrayList<>();
        products.ifPresent(res::add);
        //  if (products.isPresent()) {
        model.addAttribute("product", res);
        return "product_edit";
    }

    @GetMapping("/ship{productId}")
    public String addShip(Model model, @PathVariable("productId") Long productId, Principal user) {
//        Optional<Product> shipList = ShipRepo.findAllById();
//        ArrayList<Product> res = new ArrayList<>();
//        products.ifPresent(res::add);




        Ship ship = new Ship(userRepo.findByUsername(user.getName()).getId(), productId);
        ShipRepo.save(ship);
    //    model.addAttribute("shiplist", res);
        return "product_edit";
    }

}
