package tw.pers.allen.pawposter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import tw.pers.allen.pawposter.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByMemberMail(String email);

}
