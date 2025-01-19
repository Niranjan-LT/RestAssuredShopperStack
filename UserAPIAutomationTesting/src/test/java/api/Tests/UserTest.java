package api.Tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.Payload.User;
import api.Routes.UserEndpoints;
import io.restassured.response.Response;

public class UserTest {

	Faker faker;
	User user;
	@BeforeClass
	public void setup() {
		faker = new Faker();
		user = new User();
		user.setId(faker.idNumber().hashCode());
		user.setUsername(faker.name().username());
		user.setFirstname(faker.name().firstName());
		user.setLastname(faker.name().lastName());
		user.setEmail(faker.internet().emailAddress());
		user.setPassword(faker.internet().password(5, 10));
		user.setPhone(faker.phoneNumber().cellPhone());
	}
	@Test(priority = 1)
	public void createUserTest() 
	{
		Response response = UserEndpoints.createuser(user);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);	
	}
	@Test(priority = 2, dependsOnMethods = { "createUserTest" })
	public void getUserTest() 
	{
		Response response = UserEndpoints.getuser(this.user.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority = 3, dependsOnMethods = { "getUserTest" })
	public void updateUserTest() {
		user.setFirstname(faker.name().firstName());
		user.setLastname(faker.name().lastName());
		user.setEmail(faker.internet().emailAddress());
		Response response = UserEndpoints.updateuser(this.user.getUsername(), user);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		// check after update
		Response response1 = UserEndpoints.getuser(this.user.getUsername());
		response1.then().log().all();
		System.out.println(response1.asPrettyString());
		Assert.assertEquals(response1.getStatusCode(), 200);
	}
	//@Test(priority = 4, dependsOnMethods = {"updateUserTest" })
	public void deleteUserTest() 
	{
		Response response = UserEndpoints.deleteuser(this.user.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
}
