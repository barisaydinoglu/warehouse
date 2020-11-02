package info.aydinoglu.baris.warehouse.exception;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import info.aydinoglu.baris.warehouse.TestDataUtil;
import info.aydinoglu.baris.warehouse.service.ArticleService;
import info.aydinoglu.baris.warehouse.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class WarehouseExceptionHandlerTest {

  @MockBean
  private ArticleService articleService;

  @MockBean
  private ProductService productService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(roles = "USER")
  void testNotFoundExceptionHandler() throws Exception {
    //GIVEN
    val id = TestDataUtil.createRandomInt();
    when(articleService.getArticle(id)).thenThrow(new ArticleNotFoundException(id));
    //WHEN
    val resultActions = mockMvc.perform(get("/articles/" + id)).andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isNotFound())
        .andExpect(content().string(String.format("Could not find article '%s'", id)));
  }

  @Test
  @WithMockUser(roles = "USER")
  void testNotAvailableInStockExceptionHandler() throws Exception {
    //GIVEN
    val name = TestDataUtil.createRandomString(10);
    when(productService.getProduct(name))
        .thenThrow(new ProductArticleNotAvailableInStockException(name));
    //WHEN
    val resultActions = mockMvc.perform(get("/products/" + name)).andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isNotFound())
        .andExpect(
            content().string(
                String.format("Articles for product '%s' are not available in stock.", name)));
  }
}
