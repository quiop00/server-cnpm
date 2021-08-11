package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.Post;
import doancnpm.payload.request.PostRequest;

@Component
public class PostConverter {

	public Post toEntity(PostRequest dto) {
		Post entity = new Post();
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setAddress(dto.getAddress());
		return entity;
	}

	public PostRequest toDTO(Post entity) {
		PostRequest dto = new PostRequest();
		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		dto.setAddress(entity.getAddress());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedDate(entity.getModifiedDate());

		return dto;
	}

	public Post toEntity(PostRequest dto, Post entity) {

		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setAddress(dto.getAddress());
		return entity;
	}
}
