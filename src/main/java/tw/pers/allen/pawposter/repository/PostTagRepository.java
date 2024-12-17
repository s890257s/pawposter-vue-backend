package tw.pers.allen.pawposter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Integer> {

}
