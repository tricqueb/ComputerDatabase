package com.excilys.computerdatabase.mappers.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.dto.impl.CompanyDTOImpl;
import com.excilys.computerdatabase.mappers.Mapper;

public class CompanyMapperImpl implements Mapper<CompanyDTOImpl, Company> {

	@Override
	public Company map(CompanyDTOImpl companydto) {
		if (companydto != null)
			if (!"null".contentEquals(companydto.getId())) {

				Company.Builder builder = Company.Builder();
				if (!companydto.getId().isEmpty())
					builder.id(Long.parseLong(companydto.getId()));
				builder.name(companydto.getName());

				return builder.build();
			}
		return null;
	}

	@Override
	public CompanyDTOImpl invert(Company company) {
		return CompanyDTOImpl.Builder()
				.id(String.valueOf(company.getId()))
				.name(company.getName())
				.build();
	}

	@Override
	public List<Company> map(List<CompanyDTOImpl> companyDtoList) {
		List<Company> companyList = new ArrayList<Company>();
		for (CompanyDTOImpl cdto : companyDtoList) {
			companyList.add(map(cdto));
		}
		return companyList;

	}

	@Override
	public List<CompanyDTOImpl> invert(List<Company> companyList) {
		List<CompanyDTOImpl> companyDtoList = new ArrayList<CompanyDTOImpl>();
		for (Company cdto : companyList) {
			companyDtoList.add(invert(cdto));
		}
		return companyDtoList;
	}
}