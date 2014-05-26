package com.excilys.computerdatabase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.repository.ComputerRepository;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.ComputerService;

@Transactional(readOnly = true)
@Service
public class ComputerServiceImpl implements ComputerService {

	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Autowired
	private ComputerRepository computerRepository;

	public ComputerServiceImpl() {
	}

	// FIXME No check on company id !
	// Adding new computer to db
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#create(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Transactional(readOnly = false)
	@Override
	public void create(Computer computer) {
		logger.warn("Ajout de {}", computer.getName());
		computerRepository.save(computer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#delete(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Transactional(readOnly = false)
	@Override
	public void delete(Computer computer) {
		logger.debug("Deleting {} ", computer.getId());
		computerRepository.delete(computer);
		logger.warn("Element {} has been deleted", computer.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#find(java.
	 * lang.Long)
	 */

	@Override
	public Computer find(Long id) {
		logger.debug("Reading for {} ", id);
		return computerRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#count(java
	 * .lang.String)
	 */

	@Override
	public Long count(String search) {
		logger.debug("Counting {} elements", search);
		return computerRepository.countByNameContainingOrCompany_NameContaining(
				search, search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#find(java.
	 * lang.String, java.lang.Integer, java.lang.Integer, java.lang.Long,
	 * java.lang.Boolean)
	 */

	@Override
	public Page<Computer> find(String computerName, Pageable page) {
		logger.debug("Reading {} elements", computerName);
		return computerRepository.findByNameContainingOrCompany_NameContaining(
				computerName, computerName, page);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#update(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Transactional(readOnly = false)
	@Override
	public void update(Computer computer) {
		logger.warn("Updating computer {} ({}) with new values:  {} {} {}",
				computer.getName(), computer.getId(), computer.getIntroduced(),
				computer.getDiscontinued(), computer.getCompany());
		computerRepository.save(computer);
		logger.debug("Update done");
	}

}
