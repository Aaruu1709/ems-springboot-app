package com.aaruu.ems.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaruu.ems.jwt.JwtService;

@RestController

@RequestMapping("/auth")

public class AuthController {

	@Autowired
	private JwtService jwtService;

	@PostMapping("/login")

	public String login(

			@RequestBody LoginRequest request

	) {

		if (

		request.getUsername().equals("jack")

				&&

				request.getPassword().equals("123")

		) {

			return jwtService.generateToken(

					request.getUsername()

			);

		}

		return "INVALID CREDENTIALS";

	}

}

//Authentication is the process of verifying the identity of a user or system before granting access to an application or resource.

//Simple version:
//"Authentication validates user identity using credentials like username/password, token, OAuth or biometrics before allowing access
//Authentication answers → WHO are you?
//

//"Authentication and authorization work together. 
//Authentication verifies identity, while authorization controls permissions.
//In Spring Security, authentication happens first and authorization 
//is applied based on roles or authorities."

//User login success but cannot delete employee.
//problem:authorization
//"Authorization means user logged in successfully, so authentication is completed. 
//User is valid, but according to role we authorize them and may not give permission 
//to delete employee.

//login-> Authentication->token->role check->Authorization->controller

//Authentication verifies whether user identity is valid. 
//After successful authentication, authorization checks user's roles and permissions 
//to decide whether user can perform operations like delete,
//update or access protected APIs.