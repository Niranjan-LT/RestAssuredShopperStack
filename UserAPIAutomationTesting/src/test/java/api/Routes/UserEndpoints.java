package api.Routes;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.Payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints {

	public static Response createuser(User payload) 
	{
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
		.when().post(Routes.posturl);

		return response;
	}
	public static Response getuser(String username)
	{
		Response response=given().pathParam("username", username).get(Routes.geturl);
		
		return response;
	}
	
	public static Response updateuser(String username,User payload)
	{
		Response response=given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("username", username).body(payload).put(Routes.puturl);
		return response;
	}
	
	public static Response deleteuser(String username)
	{
		Response response=given().pathParam("username", username).delete(Routes.deleteurl);
		
		return response;
	}
}
