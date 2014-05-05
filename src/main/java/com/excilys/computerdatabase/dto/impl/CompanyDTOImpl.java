package com.excilys.computerdatabase.dto.impl;

public class CompanyDTOImpl {
	// Builder
	public static class Builder {
		CompanyDTOImpl companyDto;

		private Builder() {
			companyDto = new CompanyDTOImpl();
		}

		public Builder id(String id) {
			companyDto.id = id;
			return this;
		}

		public Builder name(String name) {
			companyDto.name = name;
			return this;
		}

		public CompanyDTOImpl build() {
			return companyDto;
		}
	}

	public static Builder Builder() {
		return new Builder();
	}

	// Getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String id = "";
	private String name = "";

}
