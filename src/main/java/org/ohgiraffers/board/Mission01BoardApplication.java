// 모든 프로그램에는 프로그램 시작을 담당하는 파일이있는데,

//스프링부트에선 '프로젝트명+application.java' 파일이다.
//아~  여기선 Misstion01BoardApplication.java군..


package org.ohgiraffers.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   //시작이군.
public class Mission01BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(Mission01BoardApplication.class, args);
    }

    //맞네 main도있네..

}

//그럼 포스트서비스에서,