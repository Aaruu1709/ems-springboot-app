package com.aaruu.ems.util;

import com.aaruu.ems.payload.ApiResponse;

public class ResponseUtil {
	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(message, 200, data);
	}
}
