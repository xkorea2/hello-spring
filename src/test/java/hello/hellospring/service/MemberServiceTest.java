package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository(); //service 에 MemberService 에 선언된 객체랑 다르다. 이걸 맞춰줘야한다.
    MemoryMemberRepository memberRepository; // 이렇게 변경하고 BeforeEach 를 해주면 됨.

    @BeforeEach //각 테스트를 실행하기전에 실행됨.
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();    //MemoryMemberRepository를 먼저 만들고,
        memberService = new MemberService(memberRepository);    //memberRepository 를 MemberService에 넣어준다. 같은 메모리 객체를 사용하게 됨.
    }



    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {   //테스트할 때, 요즘엔 많이 한글로 적기도 함. 직관적이어서 좋음. 빌드될 때 실제코드에 포함되지 않음.
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");  //회원이름 같게해서 예외 발생하게 해보자.
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
