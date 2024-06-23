package com.stockprocessor.stockprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockprocessor.stockprocessor.db.ProductEntity;
import com.stockprocessor.stockprocessor.db.ProductQtyEntity;
import com.stockprocessor.stockprocessor.db.repository.ProductRepository;
import com.stockprocessor.stockprocessor.dto.ProductDTO;
import com.stockprocessor.stockprocessor.mapper.ProductMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    private static final String INIT_SQL = "db/init_data.sql";

    public static final String PRODUCT_SHORT_CODE_DEFAULT = "KZIZ";


    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:13.1-alpine"
    ).withDatabaseName("stock-db")
            .withUsername("stock-user")
            .withPassword("stock-pass")
            .withInitScript(INIT_SQL)
            .withReuse(true);

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();

    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Before
    public void setUp() throws Exception {
        //todo for some weird reason, initData doesn't setup data before test runs so had to be called during each method required
        this.initData();
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.tearDownData();
    }

    @Test
    @Order(1)
    public void shouldCreateProductWith201StatusAndCorrectLocationHeaderValue() throws Exception {

        String productShortCodeNew = "OXRT" + RandomUtils.nextInt();

        ProductDTO productDTO = getProductDTO("TV", productShortCodeNew);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    @Order(2)
    public void shouldCreateProductWith409Conflict() throws Exception {
        this.initData();

        ProductDTO productDTO = getProductDTO("TV", PRODUCT_SHORT_CODE_DEFAULT);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @Order(3)
    public void shouldUpdateProductAndExpect204NoContent() throws Exception {
        this.initData();

        ProductDTO productDTO = getProductDTO("AMGR", PRODUCT_SHORT_CODE_DEFAULT);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + getCurrentSavedProductEntity().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    @Order(4)
    public void shouldGetAllProductsExpect200AndNotEmptyContent() throws Exception {
        this.initData();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("productShortCode")));

    }

    private ProductDTO getProductDTO(String productName, String productShortCode) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(productName);
        productDTO.setProductShortCode(productShortCode);
        productDTO.setProductImageURL("https://whatever.org");
        productDTO.setProductQty(RandomUtils.nextInt());
        return productDTO;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("Hello");
        productEntity.setProductImageURL("https://yesman.org/img");
        productEntity.setProductShortCode(PRODUCT_SHORT_CODE_DEFAULT);

        ProductQtyEntity productQtyEntity = new ProductQtyEntity(5000);
        productQtyEntity.setProductEntity(productEntity);

        productEntity.setProductQty(productQtyEntity);

        this.productRepository.save(productEntity);

        this.productRepository.flush();

        System.out.println("saved Product : &&&&&&&&&&&&&&&&&&&&&&&&&&&&& " + asJsonString(getCurrentSavedProductEntity()) + " &&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    }

    private ProductEntity getCurrentSavedProductEntity() {
        return this.productRepository.findByProductShortCode(PRODUCT_SHORT_CODE_DEFAULT).get();
    }

    private void tearDownData() {
        productRepository.deleteAll();
        productRepository.flush();
    }
}