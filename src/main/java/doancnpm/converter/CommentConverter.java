package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.Comment;

import doancnpm.payload.request.CommentRequest;


@Component
public class CommentConverter {
	public Comment toComment(CommentRequest commentRequest)
	{
		Comment comment = new Comment();
		comment.setContent(commentRequest.getContent());
		return comment;
	}
}
