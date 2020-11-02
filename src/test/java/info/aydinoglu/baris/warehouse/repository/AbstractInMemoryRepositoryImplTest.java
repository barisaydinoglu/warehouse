package info.aydinoglu.baris.warehouse.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import info.aydinoglu.baris.warehouse.TestDataUtil;
import lombok.val;
import org.junit.jupiter.api.Test;

abstract class AbstractInMemoryRepositoryImplTest<T, ID> {

  protected GenericRepository<T, ID> repository;

  @Test
  void testSave() {
    //GIVEN
    val entity = createTestEntity();
    //WHEN
    val result = repository.save(entity);
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result).isEqualTo(entity),
        () -> assertThat(repository.count()).isEqualTo(1)
    );
  }

  @Test
  void testSaveAll() {
    //GIVEN
    val entities = TestDataUtil.createTestEntities(this::createTestEntity);
    //WHEN
    val result = repository.saveAll(entities);
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result.size()).isEqualTo(entities.size()),
        () -> assertThat(repository.count()).isEqualTo(entities.size())
    );
  }

  @Test
  void testFindById() {
    //GIVEN
    val entity = createTestEntity();
    repository.save(entity);
    //WHEN
    val result1 = repository.findById(resolveId(entity));
    val result2 = repository.findById(resolveNonExistingId());
    //THEN
    assertAll(
        () -> assertThat(result1).isNotNull(),
        () -> assertThat(result1).isPresent(),
        () -> assertThat(result1.orElse(null)).isEqualTo(entity),
        () -> assertThat(result2).isNotNull(),
        () -> assertThat(result2).isEmpty()
    );
  }

  @Test
  void testExistsById() {
    //GIVEN
    val entity = createTestEntity();
    repository.save(entity);
    //WHEN
    val result1 = repository.existsById(resolveId(entity));
    val result2 = repository.existsById(resolveNonExistingId());
    //THEN
    assertAll(
        () -> assertThat(result1).isTrue(),
        () -> assertThat(result2).isFalse()
    );
  }

  @Test
  void testFindAll() {
    //GIVEN
    val entities = TestDataUtil.createTestEntities(this::createTestEntity);
    repository.saveAll(entities);
    //WHEN
    val result = repository.findAll();
    //THEN
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result.size()).isEqualTo(entities.size()),
        () -> assertThat(result).containsAll(entities)
    );
  }

  @Test
  void testCount() {
    //GIVEN
    val entities = TestDataUtil.createTestEntities(this::createTestEntity);
    repository.saveAll(entities);
    //WHEN
    val result = repository.count();
    //THEN
    assertAll(
        () -> assertThat(result).isEqualTo(entities.size())
    );
  }

  @Test
  void testDeleteById() {
    //GIVEN
    val entities = TestDataUtil.createTestEntities(this::createTestEntity);
    val entityToBeDeleted = createTestEntity();
    repository.saveAll(entities);
    repository.save(entityToBeDeleted);
    //WHEN
    repository.deleteById(resolveId(entityToBeDeleted));
    //THEN
    val result = repository.findAll();
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result.size()).isEqualTo(entities.size()),
        () -> assertThat(result).containsAll(entities),
        () -> assertThat(result).doesNotContain(entityToBeDeleted)
    );
  }

  @Test
  void testDeleteAll() {
    //GIVEN
    val entities = TestDataUtil.createTestEntities(this::createTestEntity);
    repository.saveAll(entities);
    //WHEN
    repository.deleteAll();
    //THEN
    val result = repository.findAll();
    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result.size()).isZero()
    );
  }

  protected abstract T createTestEntity();

  protected abstract ID resolveId(T entity);

  protected abstract ID resolveNonExistingId();
}
