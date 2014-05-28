package com.excilys.computerdatabase.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;
import com.excilys.computerdatabase.mapper.Mapper;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ComputerMapperImpl implements Mapper<ComputerDTO, Computer> {
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapperImpl.class);
	CompanyMapperImpl companyMapper = new CompanyMapperImpl();

	@Autowired
	MessageSource messageSource;

	@Override
	public Computer map(ComputerDTO computerDTO) {
		logger.debug("mapping data...");

		// Date pattern
		String dateFormat = messageSource.getMessage("format.date",
				new Object[] {}, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);

		Computer.Builder builder = Computer.Builder();

		if (computerDTO.getId() != null && !computerDTO.getId().isEmpty())
			builder.id(Long.parseLong(computerDTO.getId()));

		if (computerDTO.getName() != null)
			builder.name(computerDTO.getName());

		if (computerDTO.getIntroduced() != null)
			if (!computerDTO.getIntroduced().isEmpty())
				builder.introduced(formatter.parseDateTime(computerDTO.getIntroduced()));

		if (computerDTO.getDiscontinued() != null)
			if (!computerDTO.getDiscontinued().isEmpty())
				builder.discontinued(formatter.parseDateTime(computerDTO.getDiscontinued()));

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
