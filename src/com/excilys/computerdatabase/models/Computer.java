package com.excilys.computerdatabase.models;

import java.util.Date;

public class Computer {

	public static class Builder {
		Computer computer;

		private Builder() {
			computer = new Computer();
		}

		public Builder id(Long id) {
			computer.id = id;
			return this;
		}

		public Builder name(String name) {
			computer.name = name;
			return this;
		}

		public Builder introduced(Date introduced) {
			computer.introduced = introduced;
			return this;
		}

		public Builder discontinued(Date discontinued) {
			computer.discontinued = discontinued;
			return this;
		}

		public Builder company(Company company) {
			computer.company = company;
			return this;
		}

		public Computer build() {
			return computer;
		}
	}

	public static Builder Builder() {
		return new Builder();
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
