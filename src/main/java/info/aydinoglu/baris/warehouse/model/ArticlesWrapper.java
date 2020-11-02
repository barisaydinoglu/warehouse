package info.aydinoglu.baris.warehouse.model;

import java.util.List;
import lombok.Data;

@Data
public class ArticlesWrapper {

  private List<Article> inventory;
}
