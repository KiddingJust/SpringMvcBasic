package com.example.spinrgmvcbasic.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음.
 * 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    //싱글톤 되도록 했으므로 static 없어도 됨. 흠.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    //지금은 스프링 쓰는 것 아님. 싱글톤 만드는 것.
    private static final MemberRepository instance = new MemberRepository();

    //private으로 생성 막아서 싱글톤 유지되도록
    public static MemberRepository getInstance(){
        return instance;
    }
    private MemberRepository(){

    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
