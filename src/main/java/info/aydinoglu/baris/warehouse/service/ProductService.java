package info.aydinoglu.baris.warehouse.service;

import info.aydinoglu.baris.warehouse.model.Product;
import java.util.List;

public interface ProductService {

  List<Product> saveProducts(List<Product> products);

  List<Product> getProducts();

  Product getProduct(String name);

  Product sellProduct(String name);
}
