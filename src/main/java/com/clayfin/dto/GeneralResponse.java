package com.clayfin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.Data;

@Data
@JsonIgnoreType
public class GeneralResponse {

	private Integer status=200;
	private String message;
	private Object data;
}
