package com.excilys.computerdatabase.pages;

import lombok.Getter;
import lombok.experimental.Builder;

@Getter
@Builder
public class Pagination {

	int currentPage;
	int elementsPerPage;

	int startPage;
	int nbPages;
	int endPage;

}
