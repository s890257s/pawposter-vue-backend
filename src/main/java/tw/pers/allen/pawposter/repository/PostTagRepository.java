package tw.pers.allen.pawposter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.model.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Integer> {

	@Modifying
	@Query("DELETE FROM PostTag pt WHERE pt.post = :post")
	void deleteByPost(Post post);
}
