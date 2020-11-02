package info.aydinoglu.baris.warehouse.repository.impl;

import info.aydinoglu.baris.warehouse.repository.GenericRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractInMemoryRepositoryImpl<T, ID> implements GenericRepository<T, ID> {

  protected final Map<ID, T> repository = new HashMap<>();

  @Override
  public List<T> saveAll(final List<T> entities) {
    return entities.stream().map(this::save).collect(Collectors.toList());
  }

  @Override
  public Optional<T> findById(final ID id) {
    return existsById(id) ? Optional.of(repository.get(id)) : Optional.empty();
  }

  @Override
  public boolean existsById(final ID id) {
    return repository.containsKey(id);
  }

  @Override
  public List<T> findAll() {
    return new ArrayList<>(this.repository.values());
  }

  @Override
  public long count() {
    return repository.size();
  }

  @Override
  public void deleteById(final ID id) {
    repository.remove(id);
  }

  @Override
  public void deleteAll() {
    repository.clear();
  }
}
