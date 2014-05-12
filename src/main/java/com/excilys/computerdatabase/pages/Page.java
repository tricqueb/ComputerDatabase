package com.excilys.computerdatabase.pages;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Page<T> {

	public Page() {

		super();
		orderDirection = false;
		orderById = 2l;
		search = "";
	}

	List<T> computerList;

	Integer total;

	String search;

	Boolean orderDirection;

	Long orderById;
}
