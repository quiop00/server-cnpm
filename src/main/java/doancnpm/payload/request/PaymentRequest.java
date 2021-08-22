package doancnpm.payload.request;

public class PaymentRequest {
	private Long idBill;
	private String payer;
	private String incoming;
	private Long idPost;
	public String getIncoming() {
		return incoming;
	}
	public void setIncoming(String incoming) {
		this.incoming = incoming;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public Long getIdBill() {
		return idBill;
	}
	public void setIdBill(Long idBill) {
		this.idBill = idBill;
	}
	public Long getIdPost() {
		return idPost;
	}
	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}
	
	
}
