package com.excilys.computerdatabase.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.ComputerService;

@Transactional(readOnly = true)
@Service
public class ComputerServiceImpl implements ComputerService {

	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	@Autowired
	@Qualifier("DataSource")
	private DataSource datasource;
	@Autowired
	private ComputerDao computerDao;

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
	public void create(Computer c) {

		logger.warn("Ajout de {}", c.getName());
		computerDao.create(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#delete(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Transactional(readOnly = false)
	@Override
	public void delete(Computer c) {
		logger.debug("Deleting {} ", c.getId());
		computerDao.delete(c.getId());
		logger.warn("Element {} has been deleted", c.getName());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#find(java.
	 * lang.Long)
	 */

	@Override
	public Computer find(Long cId) {
		Computer computer = null;
		logger.debug("Reading for {} ", cId);
		computer = computerDao.find(cId);
		logger.debug("Element {} have been found", computer);
		return computer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#count(java
	 * .lang.String)
	 */

	@Override
	public Integer count(String search) {
		Integer count = null;
		logger.debug("Counting {} elements", search);
		count = computerDao.count(search);
		logger.debug("{} Element have been found", count);
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#find(java.
	 * lang.String, java.lang.Integer, java.lang.Integer, java.lang.Long,
	 * java.lang.Boolean)
	 */

	@Override
	public List<Computer> find(
			String cName,
			Integer offset,
			Integer limit,
			Long orderBy,
			Boolean desc) {
		List<Computer> cList = null;
		logger.debug("Reading {} elements", cName);
		cList = computerDao.find(cName, offset, limit, orderBy, desc);
		logger.debug("{} Element have been found", cList.size());
		return cList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#update(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Transactional(readOnly = false)
	@Override
	public void update(Computer c) {

		computerDao.update(c);
		logger.warn("Updating computer {} ({}) with new values:  {} {} {}",
				c.getName(), c.getId(), c.getIntroduced(), c.getDiscontinued(),
				c.getCompany());
		logger.debug("Update done");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ComputerService#find()
	 */

	@Override
	public List<Computer> find() {
		List<Computer> cList = null;

		logger.debug("Read all");
		cList = computerDao.find();
		logger.debug("{} Elements have been found", cList.size());
		return cList;

	}
}
