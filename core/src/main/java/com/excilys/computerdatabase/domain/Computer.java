package com.excilys.computerdatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "computer")
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
			if (introduced != null)
				computer.introduced = new DateTime(introduced);
			else
				computer.introduced = null;
			return this;
		}

		public Builder discontinued(Object discontinued) {
			if (discontinued != null)
				computer.discontinued = new DateTime(discontinued);
			else
				computer.discontinued = null;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JoinColumn(name = "company_id")
	@ManyToOne(fetch = FetchType.EAGER)
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

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(DateTime introduced) {
		this.introduced = introduced;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
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
