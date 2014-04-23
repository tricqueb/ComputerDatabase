package com.excilys.computerdatabase.dto;

public class ComputerDTO {
	// Builder
	public static class Builder {
		ComputerDTO companyDto;

		private Builder() {
			companyDto = new ComputerDTO();
		}

		public Builder id(String id) {
			companyDto.id = id;
			return this;
		}

		public Builder name(String name) {
			companyDto.name = name;
			return this;
		}

		public ComputerDTO build() {
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	private String id;
	private String company;
	private String name;
	private String introduced, discontinued;

}
