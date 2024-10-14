package br.com.desafiobackend.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.UUID;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiUsuariosTest {

    private static String baseUrl;
    private String userName;
    String password = "Test@123";
    String requestBody;

    @BeforeClass
    public static void setup() {
        baseUrl = "https://demoqa.com";
        String userName = "test" + UUID.randomUUID().toString().substring(0, 5);
    }

    //   1. Criar usuario
    @Test
    public void createUser() {
        requestBody = "{ \"userName\": \"" + userName + "\", \"password\": \"" + password + "\" }";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .post(baseUrl + "/Account/v1/User")
                .then()
                .statusCode(201)
                .extract().response();

        String userID = response.jsonPath().getString("userID");
        UserContext.setUserID(userID);

        System.out.println("User ID: " + userID);
    }

    //   2. Gerar token de acesso
    @Test
    public void generateToken() {
        String userName = "testUser";
        String password = "Test@123";

        requestBody = "{ \"userName\": \"" + userName + "\", \"password\": \"" + password + "\" }";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .post(baseUrl + "/Account/v1/GenerateToken");

        Assert.assertEquals("O código de status não é o esperado", 200, response.getStatusCode());
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull("O token não foi gerado corretamente", token);
        System.out.println("Token gerado: " + token);
    }

    // 3. Confirmar se o usuário está autorizado
    @Test
    public void userAuthorization() {
        requestBody = "{ \"userName\": \"" + userName + "\", \"password\": \"" + password + "\" }";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .post(baseUrl + "/Account/v1/Authorized");

        int statusCode = response.getStatusCode();
        Assert.assertEquals("O código de status não é o esperado", 200, statusCode);
        String responseBody = response.getBody().asString();
        System.out.println("Corpo da resposta: " + responseBody);
    }
}
