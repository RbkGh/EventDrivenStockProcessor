package com.stockprocessor.stockprocessor.controller;

import com.stockprocessor.stockprocessor.dto.ProductDTO;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mockMvc;

    private static final String INIT_SQL = "db/init_data.sql";

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:13.1-alpine"
    ).withDatabaseName("stock-db")
            .withUsername("stock-user")
            .withPassword("stock-pass")
            .withInitScript(INIT_SQL);

    @BeforeAll
    static void beforeAll() {
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

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateProductWith201Status() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Refridgerator");
        productDTO.setProductShortCode("REF");
        productDTO.setProductImageURL("https://whatever.org");
        productDTO.setProductQty(300);

        Header header = new Header("Content-Type","application/json");
        RestAssured.given()
                .when()
                .header(header)
                .body(productDTO)
                .post("/api/v1/products")
                .then()
                .statusCode(201);
    }

    @Test
    void shouldGetAllProducts() {
    }

    @Test
    void getProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}