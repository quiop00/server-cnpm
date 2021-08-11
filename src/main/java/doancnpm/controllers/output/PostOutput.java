package doancnpm.controllers.output;

import java.util.ArrayList;
import java.util.List;

import doancnpm.payload.request.PostRequest;

public class PostOutput {

	private int page;
	private int totalPage;

	private List<PostRequest> listResultDTO = new ArrayList<>();

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

	public List<PostRequest> getListResultDTO() {
		return listResultDTO;
	}

	public void setListResultDTO(List<PostRequest> listResultDTO) {
		this.listResultDTO = listResultDTO;
	}

}
