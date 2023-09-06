package com.clayfin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreType
@Getter
@Setter
 
public class GeneralResponse {

	private Integer status=200;
	private String message;
	private Object data;
}
