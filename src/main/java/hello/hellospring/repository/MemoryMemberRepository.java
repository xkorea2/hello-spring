package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {   //implements 는 인터페이스이다.

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));  //Optional.ofNullable 로 감싸면 null일때 클라이언트쪽에서 뭘 할 수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))    //filter 로 돌리면서 찾음. 람다식. member.getName()이 파라미터로 넘어온 name과 같은지 보는거임.
                .findAny(); //같은경우만 필터링 되는거고, 그중에 찾으면 반환해준다. findAny는 하나라도 찾는거임. 끝까지 돌렸는데 없으면 Optional 에 null 포함해서 반환.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store.values()는 Member 들 되겠다. (위에 private static Map<Long, Member> store로 되어있으니까)
    }

    public void clearStore() {
        store.clear();
    }
}
