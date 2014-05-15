package com.excilys.computerdatabase.page;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class Pagination {

	private Integer currentPage;
	private Integer elementsPerPage;

	private Integer startPage;
	private Integer nbPages;
	private Integer endPage;

}
