package tw.pers.allen.pawposter.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	Set<Tag> findByTagNameIn(List<String> tagNames);

	Set<Tag> findByTagIdIn(List<Integer> tagIds);

}
