package com.excilys.computerdatabase.dto.impl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.excilys.computerdatabase.dto.CompanyDTO;
import com.excilys.computerdatabase.dto.ComputerDTO;

public class ComputerDTOImpl implements ComputerDTO {
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

		public Builder companydto(CompanyDTO companyDto) {
			computerDto.companyDto = companyDto;
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

	public CompanyDTO getCompany() {
		return companyDto;
	}

	public void setCompany(CompanyDTO companyDto) {
		this.companyDto = companyDto;
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
		return "ComputerDTO [id=" + id + ", companydto=" + companyDto
				+ ", name=" + name + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + "]";
	}

	@NotNull(message = "error.00")
	private String id;
	// @Valid
	private CompanyDTO companyDto;

	@NotBlank(message = "error.10")
	@Length(min = 1, max = 255, message = "error.11")
	private String name;

	@Pattern(regexp = "(\\d{4})-(([0][0-9])|([1][0-2]))-(([0-2][0-9])|(3[0-1]))|^$", message = "error.21")
	private String introduced;
	@Pattern(regexp = "(\\d{4})-(([0][0-9])|([1][0-2]))-(([0-2][0-9])|(3[0-1]))|^$", message = "error.31")
	private String discontinued;

}
