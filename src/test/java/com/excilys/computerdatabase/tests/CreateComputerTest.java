package com.excilys.computerdatabase.tests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.dto.impl.CompanyDTOImpl;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;

@RunWith(JUnit4.class)
public class CreateComputerTest {

	@Test
	public void createTest() {
		// ComputerService cd = ComputerServiceImpl.getInstance();
		ComputerDTO computer = new ComputerDTOImpl();

		computer.setId("1");
		computer.setName("Test");
		computer.setCompany((CompanyDTOImpl.Builder().id("0").build()));
		computer.setIntroduced("");
		computer.setDiscontinued("");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ComputerDTO>> constraintViolations = validator.validateProperty(
				computer, "companyDto");

		assertTrue(!constraintViolations.isEmpty());
		// cd.update(computer);

		//
		// org.junit.Assert.assertFalse("should not be null", cd.find("poil")
		// .isEmpty());
		// System.out.println(cd.find("2654648").toString());

	}
}
