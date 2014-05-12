package com.excilys.computerdatabase.dto.impl;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.excilys.computerdatabase.dto.CompanyDTO;

public class CompanyDTOImpl implements CompanyDTO {
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.dto.impl.CompanyDTO#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.dto.impl.CompanyDTO#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.dto.impl.CompanyDTO#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.dto.impl.CompanyDTO#setName(java.lang.String
	 * )
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	private String id;

	@Length(min = 1, max = 255, message = "Company name must be between 1 and 255 characters")
	private String name;

}
