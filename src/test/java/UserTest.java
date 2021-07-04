import dto.UserPOJO;
import dto.UserResponcePOJO;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.UserAPI;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class UserTest {
    private UserAPI userAPI = new UserAPI();
    private UserPOJO getTestUser() {
        UserPOJO userPOJO = UserPOJO.builder()
                .id(777L)
                .username("vip-user")
                .firstName("Queen")
                .lastName("Elisabeth")
                .email("queen.uk@gmail.ru")
                .password("pass")
                .phone("none")
                .userStatus(1L)
                .build();

        return  userPOJO;
    }
    /**
     * Проверить, что при отправке запроса на создание пользователя:
     * 1) в респонсе в поле "type" значение "unknown"
     * 2) в поле "message" значение равно строке "<значение параметра, отпрвленного в реквесте в поле "id">"
     * 3) статус код 200
     * 4) время выполнени запроса 5 секунд
     */


    @Test
    public void checkUser() {
        UserPOJO userPOJO = getTestUser();

        Response response = userAPI.createUser(userPOJO);
        response
                .then()
                .log().all()
                .body("type", equalTo("unknown"))
                .time(lessThan(5000L))
                .statusCode(HttpStatus.SC_OK);

        String messageExpectedDto = response.as(UserResponcePOJO.class).getMessage();

        Assertions.assertEquals("777", messageExpectedDto);
    }
    /**
     * Проверить, что при отправке запроса на получение пользователя по имени ("vip-user"):
     * 1) Информация, выданная сервером, совпадает с той, что была указана при создании юзера.
     * 2) статус код 200
     * 3) время выполнени запроса 5 секунд
     */
    @Test
    public void getUserByNameCheck() {
        UserPOJO userPOJO = getTestUser();
        Response response = userAPI.getUserByName("vip-user");
        response
                .then()
                .log().all()
                .time(lessThan(5000L))
                .statusCode(HttpStatus.SC_OK);

        UserPOJO returnedUser = response.as(UserPOJO.class);
        //сравниваем 2 поджо - отправленный при создании юзера в предыдущем тесте ("vip-user") и сформированный по
        // ответу сервера. Если равны toString, то все параметры объектов совпадают
        Assertions.assertEquals(userPOJO.toString(), returnedUser.toString());
    }
}
