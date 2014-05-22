package com.excilys.computerdatabase.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "company")
public class Company {

	public static class Builder {
		Company company;

		private Builder() {
			company = new Company();
		}

		public Builder id(Long id) {
			company.id = id;
			return this;
		}

		public Builder name(String name) {
			company.name = name;
			return this;
		}

		public Company build() {
			return company;
		}
	}

	public static Builder Builder() {
		return new Builder();
	}

	@Id
	@GeneratedValue(generator = "SequenceStyleGenerator")
	@GenericGenerator(name = "SequenceStyleGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	private Long id;

	private String name;
}
