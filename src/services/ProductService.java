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
        createProduct("produto1","descricao1",10,"Joao");
        createProduct("produto2","descricao2",20,"Pedro");
        createProduct("produto3","descricao3",30,"Andre");
        createProduct("produto4","descricao4",40,"Joao");
    }
    
    public List<Product> listAll()
    {
        return products;
    }
    
    public List<Product> listByUser(String username)
    {
        List<Product> userProducts = new ArrayList<>();
        
        products.forEach(a -> {
            if (a.getUsername().equals(username))
                userProducts.add(a);
        });
        
        return userProducts;
    }
    
    public boolean buyProduct(int productId, String username)
    {
        
        return true;
    }
    
    public boolean checkIfProductExists(int productId)
    {
        for (int i = 0; i < products.size(); i++)
        {
            if (products.get(i).getId() == productId)
                return true;
        }
        return false;
    }
    
    public boolean createProduct(String name, String description, float value, String username)
    {
        if (name.equals("") || description.equals("") || value == 0)
            return false;
        
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setValue(value);
        newProduct.setUsername(username);
        
        int lastId = 0;
        if (!products.isEmpty())
            lastId = products.get(products.size()-1).getId();
        
        newProduct.setId(lastId+1);
        
        products.add(newProduct);
        
        return true;
    }
    
    public boolean editProduct(String name, String description, float value, int productId)
    {
        for (int i = 0; i < products.size(); i++)
        {
            if (products.get(i).getId() == productId)
            {
                products.get(i).setName(name);
                products.get(i).setDescription(description);
                products.get(i).setValue(value);
                
                System.out.println("editou o produto");
                System.out.println("Lista de produtos : ");
                //remover depois
                products.forEach(a -> System.out.println(a.getName()));
                System.out.println("------");
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteProduct(int productId)
    {
        for (int i = 0; i < products.size(); i++)
        {
            if (products.get(i).getId() == productId)
            {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
