package br.com.desafiobackend.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiLivrosTest {
    private final String baseUrl = "https://demoqa.com/BookStore/v1";

    @Test
    public void listBooks() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(baseUrl + "/Books")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Response Body: " + response.asString());
    }

    @Test
    public void rentBooks() {
        String userId = UserContext.getUserID();
        String isbn = "9781449325862";

        String requestBody = String.format(
                "{\"userId\": \"%s\", \"collectionOfIsbns\": [{\"isbn\": \"%s\"}]}",
                userId, isbn);

        Response response = given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/Books")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Response: " + response.asString());
    }
}
