package com.excilys.computerdatabase.pages;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

@Getter
@Builder
public class Page<T> {
	List<T> cList;
	Integer total;
	Integer currentPage, startPage, endPage, elementsPerPage;
	String search;
	Boolean desc;
	Long orderBy;
}
