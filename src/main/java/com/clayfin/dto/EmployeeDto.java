package com.clayfin.dto;

import com.clayfin.enums.RoleType;
import com.clayfin.enums.TitleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
	private TitleType title;
	private RoleType role;
	private Integer managerId;

}
