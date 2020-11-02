package info.aydinoglu.baris.warehouse.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {

  T save(T entity);

  List<T> saveAll(List<T> entities);

  Optional<T> findById(ID id);

  boolean existsById(ID id);

  List<T> findAll();

  long count();

  void deleteById(ID id);

  void deleteAll();
}
