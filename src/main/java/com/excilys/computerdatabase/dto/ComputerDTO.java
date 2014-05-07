package com.excilys.computerdatabase.dto;

public interface ComputerDTO {

	// Getters and setters
	public String getId();

	public void setId(String id);

	public CompanyDTO getCompany();

	public void setCompany(CompanyDTO companydto);

	public String getName();

	public void setName(String name);

	public String getIntroduced();

	public void setIntroduced(String introduced);

	public String getDiscontinued();

	public void setDiscontinued(String discontinued);

	@Override
	public String toString();
}
