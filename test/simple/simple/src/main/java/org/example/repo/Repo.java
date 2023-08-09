package org.example.repo;

public interface Repo<T, ID> {

    void save(T t);
    void lookup(ID id);



}
