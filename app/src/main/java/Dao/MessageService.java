package Dao;

import java.util.List;


public interface MessageService {
    public boolean save(Object objects);
    public boolean update(Object objects);
    public boolean delete(String id);
    public List showAll();
    public List showById(String id_query);
}
