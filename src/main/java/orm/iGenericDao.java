package orm;

import java.util.List;

/**
 * Created by Uliana on 19.07.2016.
 */
public interface iGenericDao <E> {

    void save(E owner) ;
    void remove (E owner);
void update (E man);
E findById(int id);
List<E> getAll() ;

}