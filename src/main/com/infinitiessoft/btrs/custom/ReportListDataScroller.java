package com.infinitiessoft.btrs.custom;

import org.jboss.seam.annotations.Name;

@Name("reportListDataScroller")
public class ReportListDataScroller {
	
	private int scrollerPage = 1;

	public Integer getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(Integer scrollerPage) {
		this.scrollerPage = scrollerPage;
	}
}
