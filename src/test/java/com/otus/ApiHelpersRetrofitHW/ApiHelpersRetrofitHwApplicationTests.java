package com.otus.ApiHelpersRetrofitHW;

import com.otus.ApiHelpersRetrofitHW.pojo.CreateUserRequest;
import com.otus.ApiHelpersRetrofitHW.pojo.CreateUserResponse;
import com.otus.ApiHelpersRetrofitHW.pojo.SingleUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;

import java.io.IOException;

@SpringBootTest
class ApiHelpersRetrofitHwApplicationTests {
	APIInterface service = APIClient.getClient().create(APIInterface.class);

	@Test
	@DisplayName("GET - GET USER BY ID")
	void getUserByIdTest() throws IOException {
		Response <SingleUserResponse> response;

		response = service.getUserById().execute();
		assert response.isSuccessful();
		System.out.println("Response SUCCESS");

		SingleUserResponse singleUserResponse = response.body();
		assert singleUserResponse != null;
		System.out.println(singleUserResponse.getData());
		System.out.println(singleUserResponse.getData().getId());
		assert singleUserResponse.getData().getId() == 2;

		System.out.println(singleUserResponse.getData().getEmail());
		assert singleUserResponse.getData().getEmail().equals("janet.weaver@reqres.in");
	}

	@Test
	@DisplayName("POST - CREATE USER")
	void checkUserCreation() throws IOException {
		Response<CreateUserResponse> responseCreateUser;
		CreateUserResponse userResponse;

		System.out.println(getCreateUserRequestBody());
		responseCreateUser = service.createUser(getCreateUserRequestBody()).execute();

		userResponse = responseCreateUser.body();
		System.out.println(userResponse.getId());
		System.out.println(userResponse.getCreatedAt());

		if(responseCreateUser.isSuccessful()){
			System.out.println("Response SUCCESS");
		}else{
			System.out.println("Response ERROR");
		}
		System.out.println(responseCreateUser.body());
		Assertions.assertEquals(201, responseCreateUser.code());
	}

	public CreateUserRequest getCreateUserRequestBody() {
		CreateUserRequest requestNewUserData = new CreateUserRequest();
		requestNewUserData.setJob("morpheus");
		requestNewUserData.setName("leader");

		return requestNewUserData;
	}

	@Test
	@DisplayName("DELETE - DELETE USER")
	void checkUserDeletion() throws IOException {
		Response<Void> responseDeleteUser;

		responseDeleteUser = service.deleteUser(2).execute();

		if(responseDeleteUser.isSuccessful()){
			System.out.println("Response SUCCESS");
		}else{
			System.out.println("Response ERROR");
		}

		Assertions.assertEquals(204, responseDeleteUser.code());
	}

	@Test
	@DisplayName("PATCH - UPDATE USER")
	void checkUserUpdating() throws IOException {
		Response<CreateUserResponse> responseUpdateUser;
		CreateUserResponse user;

		responseUpdateUser = service.updateUser(2, getUpdateUserRequestBody() ).execute();

		if(responseUpdateUser.isSuccessful()){
			System.out.println("Response SUCCESS");
		}else{
			System.out.println("Response ERROR");
		}

		user = responseUpdateUser.body();
		System.out.println(user);

		Assertions.assertEquals(200, responseUpdateUser.code());
		Assertions.assertEquals("morpheus", user.getJob());
		Assertions.assertEquals("Lawrence Fishburne", user.getName());
	}
	public CreateUserRequest getUpdateUserRequestBody() {
		CreateUserRequest requestNewUserData = new CreateUserRequest();
		requestNewUserData.setJob("morpheus");
		requestNewUserData.setName("Lawrence Fishburne");

		return requestNewUserData;
	}
}
