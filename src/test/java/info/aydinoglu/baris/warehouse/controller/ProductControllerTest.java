package info.aydinoglu.baris.warehouse.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.aydinoglu.baris.warehouse.TestDataUtil;
import info.aydinoglu.baris.warehouse.service.ArticleService;
import info.aydinoglu.baris.warehouse.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @MockBean
  private ArticleService articleService;

  @MockBean
  private ProductService productService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser(roles = "ADMIN")
  void testSaveProductsAdmin() throws Exception {
    //GIVEN
    val products = TestDataUtil.createTestEntities(TestDataUtil::createRandomProduct);
    val productsAsJSON = objectMapper.writeValueAsString(products);
    when(productService.saveProducts(products)).thenReturn(products);
    //WHEN
    val resultActions = mockMvc
        .perform(post("/products/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productsAsJSON))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json(productsAsJSON));
  }

  @Test
  @WithMockUser(roles = "USER")
  void testSaveProductsUser() throws Exception {
    //GIVEN
    val products = TestDataUtil.createTestEntities(TestDataUtil::createRandomProduct);
    val productsAsJSON = objectMapper.writeValueAsString(products);
    when(productService.saveProducts(products)).thenReturn(products);
    //WHEN
    val resultActions = mockMvc
        .perform(post("/products/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productsAsJSON))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER")
  void testGetProducts() throws Exception {
    //GIVEN
    val products = TestDataUtil.createTestEntities(TestDataUtil::createRandomProduct);
    val productsAsJSON = objectMapper.writeValueAsString(products);
    when(productService.getProducts()).thenReturn(products);
    //WHEN
    val resultActions = mockMvc
        .perform(get("/products/"))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isOk())
        .andExpect(content().json(productsAsJSON));
  }

  @Test
  @WithMockUser(roles = "USER")
  void testGetProduct() throws Exception {
    //GIVEN
    val product = TestDataUtil.createRandomProduct();
    val productAsJSON = objectMapper.writeValueAsString(product);
    when(productService.getProduct(product.getName())).thenReturn(product);
    //WHEN
    val resultActions = mockMvc
        .perform(get("/products/" + product.getName()))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isOk())
        .andExpect(content().json(productAsJSON));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testSellProductAdmin() throws Exception {
    //GIVEN
    val product = TestDataUtil.createRandomProduct();
    val productAsJSON = objectMapper.writeValueAsString(product);
    when(productService.sellProduct(product.getName())).thenReturn(product);
    //WHEN
    val resultActions = mockMvc
        .perform(delete("/products/" + product.getName()))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isOk())
        .andExpect(content().json(productAsJSON));
  }

  @Test
  @WithMockUser(roles = "USER")
  void testSellProductUser() throws Exception {
    //GIVEN
    val product = TestDataUtil.createRandomProduct();
    val productAsJSON = objectMapper.writeValueAsString(product);
    when(productService.sellProduct(product.getName())).thenReturn(product);
    //WHEN
    val resultActions = mockMvc
        .perform(delete("/products/" + product.getName()))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isForbidden());
  }
}
