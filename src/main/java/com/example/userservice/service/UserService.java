package com.example.userservice.service;

import com.example.userservice.entities.UserInfo;
import com.example.userservice.entities.UserInfoDto;
import com.example.userservice.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

	private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

	private final UserRespository userRespository;

	@Transactional
	public UserInfoDto createOrUpdateUser(UserInfoDto eventData) {
		validate(eventData);
		UnaryOperator<UserInfo> updatingUser = user -> {

			user.setEmail(eventData.getEmail());
			user.setPhoneNumber(eventData.getPhoneNumber());
			user.setFirstName(eventData.getFirstName());
			user.setLastName(eventData.getLastName());
			user.setProfilePic(eventData.getProfilePic());
			return userRespository.save(user);
		};

		Supplier<UserInfo> createUser = () -> {
			return userRespository.save(eventData.transformToUserInfo());
		};
		UserInfo userInfo = userRespository.findByUserId(eventData.getUserId())
				.map(updatingUser)
				.orElseGet(createUser);
		return new UserInfoDto(
				userInfo.getUserId(),
				userInfo.getFirstName(),
				userInfo.getLastName(),
				userInfo.getPhoneNumber(),
				userInfo.getEmail(),
				userInfo.getProfilePic()

		);
	}

	public UserInfoDto getUser(UserInfoDto userInfoDto) {
		Optional<UserInfo> userInfoOptional = userRespository.findByUserId(userInfoDto.getUserId());
		if (userInfoOptional.isEmpty()) {
			throw new RuntimeException("User not found with userId: " + userInfoDto.getUserId());
		}
		UserInfo userInfo = userInfoOptional.get();
		return new UserInfoDto(
				userInfo.getUserId(),
				userInfo.getFirstName(),
				userInfo.getLastName(),
				userInfo.getPhoneNumber(),
				userInfo.getEmail(),
				userInfo.getProfilePic()
		);
	}

	private void validate(UserInfoDto eventData) {
		if (eventData == null || eventData.getUserId() == null || eventData.getUserId().isBlank()) {
			throw new IllegalArgumentException("userId is required");
		}
		if (eventData.getEmail() == null || !EMAIL.matcher(eventData.getEmail()).matches()) {
			throw new IllegalArgumentException("email is invalid");
		}
		Long phoneNumber = eventData.getPhoneNumber();
		int digits = phoneNumber == null ? 0 : String.valueOf(Math.abs(phoneNumber)).length();
		if (phoneNumber == null || phoneNumber <= 0 || digits > 15) {
			throw new IllegalArgumentException("phoneNumber is invalid");
		}
	}

	}
