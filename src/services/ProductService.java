/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import models.Product;
import models.User;

/**
 *
 * @author Yuji
 */
public class ProductService {
    private List<Product> products;
    
    public ProductService()
    {
        products = new ArrayList<>();
        populate();
    }
    
    public void populate()
    {
        createProduct("produto1","descricao1",10,1);
        createProduct("produto2","descricao2",20,2);
        createProduct("produto3","descricao3",30,3);
        createProduct("produto4","descricao4",40,1);
    }
    
    public List<Product> listAll()
    {
        return products;
    }
    
    public List<Product> listByUser(int id)
    {
        List<Product> userProducts = new ArrayList<>();
        
        products.forEach(a -> {
            if (a.getSellerId() == id)
                userProducts.add(a);
        });
        
        return userProducts;
    }
    
    public boolean createProduct(String name, String description, float value, int sellerId)
    {
        if (name.equals("") || description.equals("") || value == 0)
            return false;
        
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setValue(value);
        newProduct.setSellerId(sellerId);
        
        int lastId = 0;
        if (!products.isEmpty())
            lastId = products.get(products.size()-1).getId();
        
        newProduct.setId(lastId+1);
        
        products.add(newProduct);
        
        System.out.println("Lista de produtos : ");
        //remover depois
        products.forEach(a -> System.out.println(a.getName()));
        System.out.println("------");
        return true;
    }
    
    public void editProduct()
    {
        
    }
    
    public void deleteProduct()
    {
        
    }
}
