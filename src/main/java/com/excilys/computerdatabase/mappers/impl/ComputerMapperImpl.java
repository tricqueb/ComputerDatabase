package com.excilys.computerdatabase.mappers.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;
import com.excilys.computerdatabase.mappers.Mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerMapperImpl implements Mapper<ComputerDTOImpl, Computer> {
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapperImpl.class);
	CompanyMapperImpl companyMapper = new CompanyMapperImpl();

	@Override
	public Computer map(ComputerDTOImpl computerDTO) {

		Computer.Builder builder = Computer.Builder();

		if (computerDTO.getId() != null)
			builder.id(Long.parseLong(computerDTO.getId()));

		if (computerDTO.getName() != null)
			builder.name(computerDTO.getName());

		if (computerDTO.getIntroduced() != null)
			if (!computerDTO.getIntroduced().isEmpty())
				builder.introduced(Date.valueOf(computerDTO.getIntroduced()));

		if (computerDTO.getDiscontinued() != null)
			if (!computerDTO.getDiscontinued().isEmpty())
				builder.discontinued(Date.valueOf(computerDTO.getDiscontinued()));

		if (computerDTO.getCompany() != null)
			builder.company(companyMapper.map(computerDTO.getCompany()))
					.build();

		return builder.build();

	}

	@Override
	public ComputerDTOImpl invert(Computer computer) {

		ComputerDTOImpl.Builder builder = ComputerDTOImpl.Builder();
		builder.id(String.valueOf(computer.getId())).name(computer.getName());
		if (computer.getIntroduced() != null)
			builder.introduced(String.valueOf(computer.getIntroduced()));
		if (computer.getDiscontinued() != null)
			builder.discontinued(String.valueOf(computer.getDiscontinued()));
		if (computer.getCompany() != null)
			builder.companydto(companyMapper.invert(computer.getCompany()));

		return builder.build();
	}

	@Override
	public List<Computer> map(List<ComputerDTOImpl> computerDtoList) {
		List<Computer> computerList = new ArrayList<Computer>();
		for (ComputerDTOImpl cdto : computerDtoList) {
			computerList.add(map(cdto));
		}
		return computerList;
	}

	@Override
	public List<ComputerDTOImpl> invert(List<Computer> computerList) {
		List<ComputerDTOImpl> computerDtoList = new ArrayList<ComputerDTOImpl>();
		for (Computer c : computerList) {
			computerDtoList.add(invert(c));
		}
		return computerDtoList;
	}
}