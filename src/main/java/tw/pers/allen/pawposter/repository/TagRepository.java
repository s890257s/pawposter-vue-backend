package tw.pers.allen.pawposter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	List<Tag> findByTagNameIn(List<String> tagNames);

	List<Tag> findByTagIdIn(List<Integer> tagIds);

}
