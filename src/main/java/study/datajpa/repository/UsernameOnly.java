package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

//인터페이스 기반 프로젝션
public interface UsernameOnly {

    // 해당 value 안에 들어있는 프로퍼티를 가지고 와서 합쳐 가지고 리턴
    @Value("#{target.username + ' ' + target.age}") // 오픈프로젝션
    String getUsername();

}

