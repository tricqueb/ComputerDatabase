package com.excilys.computerdatabase.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;
import com.excilys.computerdatabase.mapper.Mapper;

public class ComputerMapperImpl implements Mapper<ComputerDTO, Computer> {
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapperImpl.class);
	CompanyMapperImpl companyMapper = new CompanyMapperImpl();

	@Override
	public Computer map(ComputerDTO computerDTO) {
		logger.debug("mapping data...");
		Computer.Builder builder = Computer.Builder();

		if (computerDTO.getId() != null && !computerDTO.getId().isEmpty())
			builder.id(Long.parseLong(computerDTO.getId()));

		if (computerDTO.getName() != null)
			builder.name(computerDTO.getName());

		if (computerDTO.getIntroduced() != null)
			if (!computerDTO.getIntroduced().isEmpty())
				builder.introduced(computerDTO.getIntroduced());

		if (computerDTO.getDiscontinued() != null)
			if (!computerDTO.getDiscontinued().isEmpty())
				builder.discontinued(computerDTO.getDiscontinued());

		if (computerDTO.getCompany() != null)
			builder.company(companyMapper.map(computerDTO.getCompany()))
					.build();

		return builder.build();

	}

	@Override
	public ComputerDTO invert(Computer computer) {

		ComputerDTOImpl.Builder builder = ComputerDTOImpl.Builder();
		builder.id(String.valueOf(computer.getId())).name(computer.getName());
		if (computer.getIntroduced() != null)
			builder.introduced(computer.getIntroduced().toString("yyyy-MM-dd"));
		if (computer.getDiscontinued() != null)
			builder.discontinued(computer.getDiscontinued().toString(
					"yyyy-MM-dd"));
		if (computer.getCompany() != null)
			builder.companydto(companyMapper.invert(computer.getCompany()));

		return (ComputerDTO) builder.build();
	}

	@Override
	public List<Computer> map(List<ComputerDTO> computerDtoList) {
		List<Computer> computerList = new ArrayList<Computer>();
		for (ComputerDTO cdto : computerDtoList) {
			computerList.add(map(cdto));
		}
		return computerList;
	}

	@Override
	public List<ComputerDTO> invert(List<Computer> computerList) {
		List<ComputerDTO> computerDtoList = new ArrayList<ComputerDTO>();
		for (Computer c : computerList) {
			computerDtoList.add(invert(c));
		}
		return computerDtoList;
	}
}