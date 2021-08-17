package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.Comment;

import doancnpm.payload.request.CommentRequest;
import doancnpm.payload.response.CommentOutput;


@Component
public class CommentConverter {
	public Comment toComment(CommentRequest commentRequest)
	{
		Comment comment = new Comment();
		comment.setContent(commentRequest.getContent());
		return comment;
	}
	public static CommentOutput modelToResponse(Comment comment) {
		CommentOutput commentOut = new CommentOutput();
		commentOut.setId(comment.getId());
		System.out.println(comment.getStudent().getUser().getEmail()+"hello");
		commentOut.setName(comment.getStudent().getUser().getName());
		commentOut.setContent(comment.getContent());
		commentOut.setAvatar(comment.getStudent().getUser().getAvatar());
		if(comment.getStar()!=null)
			commentOut.setRate(comment.getStar());
		else commentOut.setRate(new Long(0));
		commentOut.setTime(comment.getCreatedDate().toString());
		return commentOut;
	}
}
