package dao;

import java.util.List;

public interface IDAO {
    public void persist(Object o);
    public void delete(Object id);
    public void update(Object o);
    public Object get(Object id);
    public List getAll();
}
