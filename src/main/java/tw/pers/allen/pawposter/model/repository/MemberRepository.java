package tw.pers.allen.pawposter.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.pers.allen.pawposter.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
