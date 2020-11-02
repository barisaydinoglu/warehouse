package info.aydinoglu.baris.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Article {

  @JsonProperty("art_id")
  private Integer id;

  private String name;

  private int stock;
}
