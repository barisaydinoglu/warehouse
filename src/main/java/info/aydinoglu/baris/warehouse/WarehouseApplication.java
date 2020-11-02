package info.aydinoglu.baris.warehouse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.aydinoglu.baris.warehouse.model.ArticlesWrapper;
import info.aydinoglu.baris.warehouse.model.ProductsWrapper;
import info.aydinoglu.baris.warehouse.service.ArticleService;
import info.aydinoglu.baris.warehouse.service.ProductService;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Slf4j
public class WarehouseApplication {

  public static void main(final String[] args) {
    SpringApplication.run(WarehouseApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(final ObjectMapper objectMapper,
      final ArticleService articleService,
      final ProductService productService) {
    return args -> {
      // read json and write to db
      this.loadDataFromJson(objectMapper,
          ArticlesWrapper.class,
          "inventory",
          articlesWrapper -> articleService.saveArticles(articlesWrapper.getInventory()));
      this.loadDataFromJson(objectMapper,
          ProductsWrapper.class,
          "products",
          productsWrapper -> productService.saveProducts(productsWrapper.getProducts()));
    };
  }

  private <T> void loadDataFromJson(final ObjectMapper mapper,
      final Class<T> clazz,
      final String filename,
      final Consumer<T> function) {
    final InputStream inputStream = TypeReference.class
        .getResourceAsStream(String.format("/json/%s.json",
            filename));
    try {
      final T entities = mapper.readValue(inputStream, clazz);
      function.accept(entities);
      log.info("Loaded '{}' from JSON!", filename);
    } catch (final IOException e) {
      log.error("Unable to load '{}' from JSON!", filename, e);
    }
  }
}
