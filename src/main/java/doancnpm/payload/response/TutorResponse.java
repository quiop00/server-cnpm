package doancnpm.payload.response;


import java.util.ArrayList;
import java.util.List;

import doancnpm.models.Tutor;

public class TutorResponse {
	private int page;
	private int totalPage;
	private List<Tutor> listResult = new ArrayList<>();
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<Tutor> getListResult() {
		return listResult;
	}
	public void setListResult(List<Tutor> listResult) {
		this.listResult = listResult;
	}
	
	
}
