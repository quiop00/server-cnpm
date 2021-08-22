package doancnpm.enums;

public enum NotifyType {
	//save in db
	STUDENT_INVITATION,  //for tutor	      -> id post	  
	REQUEST_TO_PAY,		 //for tutor = student accept tutor
	REQUEST_OPEN_CLASS,	 //for admin 
	PAYMENT_SUCCESS,	 
	ACCEPT_OPEN_CLASS,
	REJECT_OPEN_CLASS,	 //for student tutor
	FINISH_CLASS		 //for student tutor , student-> id class
}
