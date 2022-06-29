/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import DTOs.responses.InterestResponse;
import DTOs.responses.ProductDetailsResponse;
import java.util.ArrayList;
import java.util.List;
import models.Interest;
import models.Product;
import models.User;

/**
 *
 * @author Yuji
 */
public class ProductService {
    private List<Product> products;
    private List<Interest> interests;
    
    public ProductService()
    {
        products = new ArrayList<>();
        interests = new ArrayList<>();
        populate();
    }
    
    public void populate()
    {
        createProduct("produto1","descricao1",10,"Joao");
        createProduct("produto2","descricao2",20,"Pedro");
        createProduct("produto3","descricao3",30,"Andre");
        createProduct("produto4","descricao4",40,"Joao");
    }
    
    public void addInterest(String productName, int productId, String interestedUsername)
    {
        for (int i = 0; i < interests.size(); i++)
        {
            if (interests.get(i).getBuyerUsername().equals(interestedUsername) && interests.get(i).getProductId() == productId)
            {
                return;
            }
        }
        
        Interest toAdd = new Interest();
        toAdd.setBuyerUsername(interestedUsername);
        toAdd.setObjectName(productName);
        toAdd.setProductId(productId);
        toAdd.setProduct(getById(productId));
        interests.add(toAdd);
    }
    
    public int countInterest(String username)
    {
       int count = 0; 
        for (int i = 0; i < interests.size(); i++)
        {
            if (interests.get(i).getProduct().getUsername().equals(username))
            {
                count++;
            }
        }
        return count;
    }
    
    public List<InterestResponse> listInterests(String username)
    {
        List<InterestResponse> response = new ArrayList<>();
        for (int i = 0; i < interests.size(); i++)
        {
            if (interests.get(i).getProduct().getUsername().equals(username))
            {
                InterestResponse toAdd = new InterestResponse();
                toAdd.setBuyerUsername(interests.get(i).getBuyerUsername());
                toAdd.setObjectName(interests.get(i).getObjectName());
                toAdd.setProductID(interests.get(i).getProductId());
                response.add(toAdd);
            }
        }
        return response;
    }
    
    public Product getById(int productId)
    {
        for (int i = 0; i < products.size(); i++)
        {
            if (products.get(i).getId() == productId)
            {
                return products.get(i);
            }
        }
        return null;
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
    
    public ProductDetailsResponse productDetails(int productId, String username)
    {
        
        for (int i = 0; i < products.size(); i++)
        {
            if (products.get(i).getId() == productId)
            {
                ProductDetailsResponse p = new ProductDetailsResponse();
                p.setDescription(products.get(i).getDescription());
                p.setProductId(products.get(i).getId());
                p.setProductName(products.get(i).getName());
                p.setProductValue(products.get(i).getValue());
                p.setStatusCode(601);
                p.setSellerName(products.get(i).getUsername());
                return p;
            }
        }
        return null;        
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
                for (int j = 0; j < interests.size(); j++)
                {
                    if (interests.get(j).getProductId() == productId)
                        interests.remove(j);
                }
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
