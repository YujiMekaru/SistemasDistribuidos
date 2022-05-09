/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infrastructure.repositories;

import java.util.List;
import models.User;

/**
 *
 * @author madeinweb
 */
public interface IUserRepository extends IRepository<User>{
    User find(String username, String password) throws Exception;
}
