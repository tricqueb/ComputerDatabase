package com.excilys.computerdatabase.dto.impl;

public class ComputerDTOImpl {
	// Builder
	public static class Builder {
		ComputerDTOImpl computerDto;

		private Builder() {
			computerDto = new ComputerDTOImpl();
		}

		public Builder id(String id) {
			computerDto.id = id;
			return this;
		}

		public Builder name(String name) {
			computerDto.name = name;
			return this;
		}

		public Builder introduced(String introduced) {
			computerDto.introduced = introduced;
			return this;
		}

		public Builder discontinued(String discontinued) {
			computerDto.discontinued = discontinued;
			return this;
		}

		public Builder companydto(CompanyDTOImpl companydto) {
			computerDto.companydto = companydto;
			return this;
		}

		public ComputerDTOImpl build() {
			return computerDto;
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

	public CompanyDTOImpl getCompany() {
		return companydto;
	}

	public void setCompany(CompanyDTOImpl companydto) {
		this.companydto = companydto;
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

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", companydto=" + companydto
				+ ", name=" + name + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + "]";
	}

	private String id;
	private CompanyDTOImpl companydto;
	private String name;
	private String introduced, discontinued;

}