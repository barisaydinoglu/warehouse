package info.aydinoglu.baris.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductArticle {

  @JsonProperty("art_id")
  private Integer articleId;

  @JsonProperty("amount_of")
  private int amount;
}
