package com.ceyda.oneSpatial;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Api_Test {

    String url = "https://reqres.in";

    @DisplayName("Get List Users")
    @Test
    public void getListUsers() {
        //send a get request and save response inside the Response object
        Response response = RestAssured.get(url + "/api/users?page=2");

        //print response status code
        System.out.println("response.statusCode() = " + response.statusCode());

        //print response body
        response.prettyPrint();

    }

    /*
   Given accept type is application/json
   When user sends get request to /api//users/2
   Then response status code must be 200
   and content type equals to application/json; charset=utf-8
   and response body id=2
   and firstName lastName is Janet Weaver
*/
    @DisplayName("Get Single User")
    @Test
    public void getSingleUser() {
        Response response = given().accept(ContentType.JSON)
                .when()
                .get(url + "/api/users/2");

        //verify status code
        assertEquals(200, response.statusCode());
        //verify content type
        assertEquals("application/json; charset=utf-8", response.header("Content-Type"));


        response.prettyPrint();

        //verify body response id = 2;
        //verify that first_name and last name is Janet Weaver.
        assertEquals((Integer) response.path("data.id"), 2);
        assertEquals(response.path("data.first_name") + " " + response.path("data.last_name").toString(), "Janet Weaver");

    }

    @DisplayName("Create Single User")
    @Test
    public void createSingleUser() {
        String requestJsonBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON).and()
                .contentType(ContentType.JSON)
                .body(requestJsonBody)
                .when()
                .post(url + "/api/users");

        assertThat(response.statusCode(), is(201));
        assertThat(response.contentType(), is("application/json; charset=utf-8"));


        assertThat(response.path("name"), is("morpheus"));
        assertThat(response.path("job"), is("leader"));


    }

    @DisplayName("Update User")
    @Test
    public void updateUser() {
        String requestJsonBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON).and()
                .contentType(ContentType.JSON)
                .body(requestJsonBody)
                .when()
                .patch(url + "/api/users/2");

        assertThat(response.statusCode(), is(200));
        assertThat(response.contentType(), is("application/json; charset=utf-8"));


        assertThat(response.path("name"), is("morpheus"));
        assertThat(response.path("job"), is("zion resident"));


    }

    @Test
    public void deleteUser() {
        String requestJsonBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON).and()
                .contentType(ContentType.JSON)
                .body(requestJsonBody)
                .when()
                .delete(url + "/api/users/2");

        assertThat(response.statusCode(), is(204));


    }


}
