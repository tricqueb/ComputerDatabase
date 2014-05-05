package com.excilys.computerdatabase.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.services.impl.ComputerServiceImpl;

@RunWith(JUnit4.class)
public class CreateComputerTest {

	@Test
	public void createTest() {
		ComputerService cd = ComputerServiceImpl.getInstance();
		Computer computer = new Computer();

		// computer.setName(null);
		// computer.setIntroduced(new Date(0));
		// computer.setDiscontinued(new Date(0));
		// cd.create(computer);

		//
		// org.junit.Assert.assertFalse("should not be null", cd.find("poil")
		// .isEmpty());
		// System.out.println(cd.find("2654648").toString());

	}
}
