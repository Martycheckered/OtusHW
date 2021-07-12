package citrus.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class FirstTest extends TestNGCitrusTestRunner {

    @Autowired
    private HttpClient getClient;

    private TestContext context;

    @Test(description = "first")
    @CitrusTest
    public void getSingleUser () {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
        .client(getClient)
        .send()
        .get("users/2")
        );

        http(httpActionBuilder -> httpActionBuilder
        .client(getClient)
        .receive()
        .response()
        .messageType(MessageType.JSON)
        .statusCode(HttpStatus.SC_OK)
        .payload("{\n" +
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
                "}")
        );
    }

    @Test(description = "second")
    @CitrusTest
    public void getListOfUsers () {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .send()
                .get("users?page=2")

        );

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .payload("{\n" +
                        "    \"page\": 2,\n" +
                        "    \"per_page\": 6,\n" +
                        "    \"total\": 12,\n" +
                        "    \"total_pages\": 2,\n" +
                        "    \"data\": [\n" +
                        "        {\n" +
                        "            \"id\": 7,\n" +
                        "            \"email\": \"michael.lawson@reqres.in\",\n" +
                        "            \"first_name\": \"Michael\",\n" +
                        "            \"last_name\": \"Lawson\",\n" +
                        "            \"avatar\": \"https://reqres.in/img/faces/7-image.jpg\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 8,\n" +
                        "            \"email\": \"lindsay.ferguson@reqres.in\",\n" +
                        "            \"first_name\": \"Lindsay\",\n" +
                        "            \"last_name\": \"Ferguson\",\n" +
                        "            \"avatar\": \"https://reqres.in/img/faces/8-image.jpg\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 9,\n" +
                        "            \"email\": \"tobias.funke@reqres.in\",\n" +
                        "            \"first_name\": \"Tobias\",\n" +
                        "            \"last_name\": \"Funke\",\n" +
                        "            \"avatar\": \"https://reqres.in/img/faces/9-image.jpg\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 10,\n" +
                        "            \"email\": \"byron.fields@reqres.in\",\n" +
                        "            \"first_name\": \"Byron\",\n" +
                        "            \"last_name\": \"Fields\",\n" +
                        "            \"avatar\": \"https://reqres.in/img/faces/10-image.jpg\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 11,\n" +
                        "            \"email\": \"george.edwards@reqres.in\",\n" +
                        "            \"first_name\": \"George\",\n" +
                        "            \"last_name\": \"Edwards\",\n" +
                        "            \"avatar\": \"https://reqres.in/img/faces/11-image.jpg\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 12,\n" +
                        "            \"email\": \"rachel.howell@reqres.in\",\n" +
                        "            \"first_name\": \"Rachel\",\n" +
                        "            \"last_name\": \"Howell\",\n" +
                        "            \"avatar\": \"https://reqres.in/img/faces/12-image.jpg\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"support\": {\n" +
                        "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                        "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "    }\n" +
                        "}")
        );
    }

    @Test(description = "third")
    @CitrusTest
    public void createUser () {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .send()
                .post("users")
                .payload("{\n" +
                        "    \"name\": \"mozart\",\n" +
                        "    \"job\": \"composer\"\n" +
                        "}")
        );

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .statusCode(HttpStatus.SC_CREATED)
                .validate("name", "mozart")
                .validate("job", "composer")
                .validate("id", "@matches('[0-9]')@")

        );
    }

    @Test(description = "fourth")
    @CitrusTest
    public void deleteUser () {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .send()
                .delete("users/2")

        );

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .statusCode(HttpStatus.SC_NO_CONTENT)

        );
    }

    @Test(description = "fifth")
    @CitrusTest
    public void getSingleResource () {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .send()
                .get("unknown/2")
        );

        http(httpActionBuilder -> httpActionBuilder
                .client(getClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .payload("{\n" +
                        "    \"data\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"name\": \"fuchsia rose\",\n" +
                        "        \"year\": 2001,\n" +
                        "        \"color\": \"#C74375\",\n" +
                        "        \"pantone_value\": \"17-2031\"\n" +
                        "    },\n" +
                        "    \"support\": {\n" +
                        "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                        "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "    }\n" +
                        "}")
        );
    }
}
