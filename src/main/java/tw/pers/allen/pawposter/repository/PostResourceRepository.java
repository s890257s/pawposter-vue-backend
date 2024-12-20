package tw.pers.allen.pawposter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.model.entity.PostResource;

public interface PostResourceRepository extends JpaRepository<PostResource, Integer> {

	@Modifying
	void deleteByPost(Post post);
}
