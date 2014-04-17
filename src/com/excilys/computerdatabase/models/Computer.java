package com.excilys.computerdatabase.models;

import java.util.Date;

public class Computer {

	public Computer(Company company, String name, Date introduced,
			Date discontinued) {
		super();
		this.id = id;
		this.company = company;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public Computer() {
		super();
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", companyId=" + company + ", name="
				+ name + ", introduce=" + introduced + ", discontinued="
				+ discontinued + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company companyId) {
		this.company = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	private long id;
	private Company company;
	private String name;
	private Date introduced, discontinued;

}
