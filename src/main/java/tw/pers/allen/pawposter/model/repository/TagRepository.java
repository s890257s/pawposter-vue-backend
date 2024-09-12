package tw.pers.allen.pawposter.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
