package info.aydinoglu.baris.warehouse.controller;

import static org.mockito.Mockito.when;
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

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

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
  void testSaveArticlesAdmin() throws Exception {
    //GIVEN
    val articles = TestDataUtil.createTestEntities(TestDataUtil::createRandomArticle);
    val articlesAsJSON = objectMapper.writeValueAsString(articles);
    when(articleService.saveArticles(articles)).thenReturn(articles);
    //WHEN
    val resultActions = mockMvc
        .perform(post("/articles/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(articlesAsJSON))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json(articlesAsJSON));
  }

  @Test
  @WithMockUser(roles = "USER")
  void testSaveArticlesUser() throws Exception {
    //GIVEN
    val articles = TestDataUtil.createTestEntities(TestDataUtil::createRandomArticle);
    val articlesAsJSON = objectMapper.writeValueAsString(articles);
    when(articleService.saveArticles(articles)).thenReturn(articles);
    //WHEN
    val resultActions = mockMvc
        .perform(post("/articles/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(articlesAsJSON))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER")
  void testGetArticles() throws Exception {
    //GIVEN
    val articles = TestDataUtil.createTestEntities(TestDataUtil::createRandomArticle);
    val articlesAsJSON = objectMapper.writeValueAsString(articles);
    when(articleService.getArticles()).thenReturn(articles);
    //WHEN
    val resultActions = mockMvc
        .perform(get("/articles/"))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isOk())
        .andExpect(content().json(articlesAsJSON));
  }

  @Test
  @WithMockUser(roles = "USER")
  void testGetArticle() throws Exception {
    //GIVEN
    val article = TestDataUtil.createRandomArticle();
    val articleAsJSON = objectMapper.writeValueAsString(article);
    when(articleService.getArticle(article.getId())).thenReturn(article);
    //WHEN
    val resultActions = mockMvc
        .perform(get("/articles/" + article.getId()))
        .andDo(print());
    //THEN
    resultActions.andDo(print()).andExpect(status().isOk())
        .andExpect(content().json(articleAsJSON));
  }
}
