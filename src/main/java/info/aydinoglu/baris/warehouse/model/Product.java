package info.aydinoglu.baris.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class Product {

  private String name;

  @JsonProperty("contain_articles")
  private List<ProductArticle> containArticles;
}
