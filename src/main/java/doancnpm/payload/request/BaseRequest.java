package doancnpm.payload.request;

import java.util.ArrayList;
import java.util.List;

public class BaseRequest<T> {

	private Long id;

	private List<T> listResult = new ArrayList<>();

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<T> getListResult() {
		return listResult;
	}

	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
	}

}
