package com.excilys.computerdatabase.dto;

import javax.validation.constraints.NotNull;

public interface CompanyDTO {

	// Getters and setters

	@NotNull
	public String getId();

	public void setId(String id);

	@NotNull
	public String getName();

	public void setName(String name);

}