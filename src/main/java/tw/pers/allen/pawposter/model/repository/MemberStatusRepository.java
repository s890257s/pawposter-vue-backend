package tw.pers.allen.pawposter.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.MemberStatus;

public interface MemberStatusRepository extends JpaRepository<MemberStatus, Integer> {

}
