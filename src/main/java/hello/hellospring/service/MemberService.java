package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository(); //이렇게 해주지말고 외부에서 직접 넣어주도록 아래 코드에 ㅈ
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;   //외부에서 입력하도록 한다. 이것이 DI(의존성 주입)방식이다.
    }

    /**
     * 회원가입
     */
    public Long join (Member member) {
        validateDuplicateMember(member);    //중복회원 검증
        memberRepository.save(member);  //위 코드에서 통과되면 세이브
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
