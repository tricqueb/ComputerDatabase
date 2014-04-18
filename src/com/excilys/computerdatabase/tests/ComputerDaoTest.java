package com.excilys.computerdatabase.tests;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.models.Computer;

@RunWith(JUnit4.class)
public class ComputerDaoTest {

	@Test
	public void createTest() {
		ComputerDao cd = ComputerDao.getInstance();
		Computer computer = new Computer();

		computer.setName("poil");
		computer.setIntroduced(new Date(0));
		computer.setDiscontinued(new Date(0));
		cd.create(computer);

		org.junit.Assert.assertFalse("should not be null", cd.find("poil")
				.isEmpty());
		System.out.println(cd.find("2654648").toString());

	}
}
