package tw.pers.allen.pawposter.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
