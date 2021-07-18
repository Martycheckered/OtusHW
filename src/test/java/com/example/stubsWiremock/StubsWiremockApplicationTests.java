package com.example.stubsWiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
class StubsWiremockApplicationTests {

	private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

	@BeforeAll
	public static void setUpMockServer() {
		wireMockServer.start();
		WireMock.configureFor("localhost", 5050);
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users/2"))
				.willReturn(WireMock.aResponse()
						.withStatus(200)
						.withBody("{\n" +
								"    \"data\": {\n" +
								"        \"id\": 2,\n" +
								"        \"email\": \"janet.weaver@reqres.in\",\n" +
								"        \"first_name\": \"Janet\",\n" +
								"        \"last_name\": \"Weaver\",\n" +
								"        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
								"    },\n" +
								"    \"support\": {\n" +
								"        \"url\": \"https://reqres.in/#support-heading\",\n" +
								"        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
								"    }\n" +
								"}")));
		WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/api/users"))
				.withRequestBody(equalToJson("{\n" +
						"    \"name\": \"morpheus\",\n" +
						"    \"job\": \"leader\"\n" +
						"}"))
				.willReturn(WireMock.aResponse()
						.withStatus(201)
						.withBody("{\n" +
								"    \"name\": \"morpheus\",\n" +
								"    \"job\": \"leader\",\n" +
								"    \"id\": \"716\",\n" +
								"    \"createdAt\": \"2021-07-18T12:42:13.331Z\"\n" +
								"}")));
		WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo("/api/users/2"))
				.willReturn(WireMock.aResponse()
						.withStatus(204)
				));
		WireMock.stubFor(WireMock.patch(WireMock.urlEqualTo("/api/users/2"))
				.withRequestBody(equalToJson("{\n" +
						"    \"name\": \"morpheus\",\n" +
						"    \"job\": \"zion resident\"\n" +
						"}"))
				.willReturn(WireMock.aResponse()
						.withStatus(201)
						.withBody("{\n" +
								"    \"name\": \"morpheus\",\n" +
								"    \"job\": \"zion resident\",\n" +
								"    \"updatedAt\": \"2021-07-18T12:50:11.857Z\"\n" +
								"}")));
	}

	@Test
	void getSingleUserTest() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
	//		.get("https://reqres.in/api/users/2")
				.get("http://localhost:5050/api/users/2")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("Janet", response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals("Weaver", response.jsonPath().getString("data.last_name"));
	}

	@Test
	void createSingleUserTest() {
		final String BODY = "{\n" +
				"    \"name\": \"morpheus\",\n" +
				"    \"job\": \"leader\"\n" +
				"}";

		Response response = given()
				.contentType(ContentType.JSON)
				.body(BODY)
				.when()
				//		.get("https://reqres.in/api/users/2")
				.post("http://localhost:5050/api/users")
				.then()
				.extract().response();

		Assertions.assertEquals(201, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("morpheus", response.jsonPath().getString("name"));
		Assertions.assertEquals("leader", response.jsonPath().getString("job"));
	}

	@Test
	void deleteSingleUserTest() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				//		.get("https://reqres.in/api/users/2")
				.delete("http://localhost:5050/api/users/2")
				.then()
				.extract().response();

		Assertions.assertEquals(204, response.statusCode());
	}

	@Test
	void updateSingleUserTest() {
		final String BODY = "{\n" +
				"    \"name\": \"morpheus\",\n" +
				"    \"job\": \"zion resident\"\n" +
				"}";

		Response response = given()
				.contentType(ContentType.JSON)
				.body(BODY)
				.when()
				//		.get("https://reqres.in/api/users/2")
				.patch("http://localhost:5050/api/users/2")
				.then()
				.extract().response();

		Assertions.assertEquals(201, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("morpheus", response.jsonPath().getString("name"));
		Assertions.assertEquals("zion resident", response.jsonPath().getString("job"));
	}

	@AfterAll
	public static void tearDownMockServer(){
		wireMockServer.stop();
	}
}
