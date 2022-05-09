/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infrastructure.repositories;

import java.util.List;

/**
 *
 * @author madeinweb
 */
public interface IRepository<T>
{
    T getById(int id) throws Exception;
    List<T> getAll() throws Exception;
    boolean insert(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete(int id) throws Exception;
}
