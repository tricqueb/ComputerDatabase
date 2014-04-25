package com.excilys.computerdatabase.mappers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.models.Computer;

public class ComputerMapper implements Mapper<ComputerDTO, Computer> {

	CompanyMapper companyMapper = new CompanyMapper();

	@Override
	public Computer map(ComputerDTO computerDTO) {

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
	public ComputerDTO invert(Computer computer) {

		ComputerDTO.Builder builder = ComputerDTO.Builder();
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
