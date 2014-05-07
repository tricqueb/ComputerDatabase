package com.excilys.computerdatabase.pages;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

@Getter
@Builder
public class Page<T> {
	List<T> computerList;
	Integer total;
	Pagination pagination;
	String search;
	Boolean orderDirection;
	Long orderById;
}
