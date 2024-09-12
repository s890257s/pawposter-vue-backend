package tw.pers.allen.pawposter.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
