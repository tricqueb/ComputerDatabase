package com.excilys.computerdatabase.models;

import java.util.Date;

public class Computer {

	public Computer(Long id, Company company, String name, Date introduced,
			Date discontinued) {
		super();
		this.id = id;
		this.company = company;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public Computer(Company company, String name, Date introduced,
			Date discontinued) {
		super();
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
		return "Computer [id=" + id + ", company=" + company + ", name=" + name
				+ ", introduced=" + introduced + ", discontinued="
				+ discontinued + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!name.isEmpty())
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

	private Long id;
	private Company company;
	private String name;
	private Date introduced, discontinued;

	public void update(String name, java.util.Date introducedDate,
			java.util.Date discontinuedDate, Company cy) {
		setName(name);
		setIntroduced(introducedDate);
		setDiscontinued(discontinuedDate);
		setCompany(cy);
	}

}
