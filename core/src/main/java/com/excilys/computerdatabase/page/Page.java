package com.excilys.computerdatabase.page;

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
		orderDirection = "ASC";
		orderByColumn = "name";
		search = "";
	}

	List<T> computerList;

	Integer total;

	String search;

	String orderDirection;

	String orderByColumn;
}
