package info.aydinoglu.baris.warehouse;

import info.aydinoglu.baris.warehouse.model.Article;
import info.aydinoglu.baris.warehouse.model.Product;
import info.aydinoglu.baris.warehouse.model.ProductArticle;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.val;

public class TestDataUtil {

  private static final String CHAR_OPTIONS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  private TestDataUtil() {
  }

  public static String createRandomString(final int length) {
    return IntStream.range(0, length)
        .mapToObj((i) -> CHAR_OPTIONS.charAt(createRandomInt(1, CHAR_OPTIONS.length() - 1)))
        .collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
            StringBuilder::toString));
  }

  public static Integer createRandomInt(final int minValue, final int maxValue) {
    return ThreadLocalRandom.current().nextInt(minValue, maxValue);
  }

  public static Integer createRandomInt(final int minValue) {
    return createRandomInt(minValue, Integer.MAX_VALUE);
  }

  public static Integer createRandomInt() {
    return createRandomInt(1, Integer.MAX_VALUE);
  }

  public static <T> List<T> createTestEntities(final Supplier<T> testEntitySupplier) {
    return IntStream
        .range(0, TestDataUtil.createRandomInt(1, 10))
        .mapToObj((i) -> testEntitySupplier.get())
        .collect(Collectors.toList());
  }

  public static Article createRandomArticle() {
    val article = new Article();
    article.setId(TestDataUtil.createRandomInt());
    article.setName(TestDataUtil.createRandomString(10));
    article.setStock(TestDataUtil.createRandomInt(0));
    return article;
  }

  public static Product createRandomProduct() {
    val product = new Product();
    product.setName(TestDataUtil.createRandomString(10));
    product.setContainArticles(createTestEntities(TestDataUtil::createRandomProductArticle));
    return product;
  }

  public static ProductArticle createRandomProductArticle() {
    val productArticle = new ProductArticle();
    productArticle.setArticleId(TestDataUtil.createRandomInt());
    productArticle.setAmount(TestDataUtil.createRandomInt());
    return productArticle;
  }
}
