package com.excilys.computerdatabase.domain;

import org.joda.time.DateTime;

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

		public Builder introduced(Object introduced) {
			computer.introduced = new DateTime(introduced);
			return this;
		}

		public Builder discontinued(Object discontinued) {
			computer.discontinued = new DateTime(discontinued);
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
		this.name = name;
	}

	public DateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(DateTime introduced) {
		this.introduced = introduced;
	}

	public DateTime getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(DateTime discontinued) {
		this.discontinued = discontinued;
	}

	private Long id;
	private String name;
	private Company company;
	private DateTime introduced, discontinued;

	public void update(
			String name,
			DateTime introducedDate,
			DateTime discontinuedDate,
			Company cy) {
		setName(name);
		setIntroduced(introducedDate);
		setDiscontinued(discontinuedDate);
		setCompany(cy);
	}

}
