package com.tolmic.digitallibrary.services.implementations;

/**
 * This interface discripts services, that provide work with main Entity in the project 
 */
public interface IMainService<T> {

    public Iterable<T> findAll();

    public T findById(Long id);

    public void save(T obj);

    public void deleteById(Long id);

}
