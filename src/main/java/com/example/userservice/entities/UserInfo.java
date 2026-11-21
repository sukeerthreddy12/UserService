package com.example.userservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo
{

	private Long id;

	@Id
	@JsonProperty("user_id")
	@NonNull
	private String userId;

	@JsonProperty("first_name")
	@NonNull
	private String firstName;

	@JsonProperty("last_name")
	@NonNull
	private String lastName;

	@JsonProperty("phone_number")
	@NonNull
	private Long phoneNumber;

	@JsonProperty("email")
	@NonNull
	private String email;

	@JsonProperty("profile_pic")
	private String profilePic;

}
