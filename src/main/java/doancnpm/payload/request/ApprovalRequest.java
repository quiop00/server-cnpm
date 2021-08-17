package doancnpm.payload.request;

public class ApprovalRequest {
	private long id;
	private Boolean approval;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Boolean getApproval() {
		return approval;
	}
	public void setApproval(Boolean approval) {
		this.approval = approval;
	}
	
}
