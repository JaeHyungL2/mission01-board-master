package org.ohgiraffers.board.config;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//스웨거 컨피규레이션은 일단

//단순 꾸미는..  스웨거의 모든기능 다 외울순없으니 ㅋ 공식문서를봐라 ㅋ

@OpenAPIDefinition(
        info=@Info(title="Board Misstion😊",
        description = "Board Mission API 명세",
        version ="v1")
//이걸로 제목쪽 화면구현했음.
        //버젼도 써줄수있음.
)
//윈도우+. = 이모지나옴
//이렇게 스웨거 작성하는법이 버전마다다름

@Configuration //어노테이션 선언해줘야함 ㅋ
public class SwaggerConfig {

    //빈으로 새로추가. 그룹드오픈api
    @Bean
    public GroupedOpenApi firstOpenApi(){
        String[] path={
                "com.ohgiraffers.board.controller"  //패스경로지정.해주고
        };


        return GroupedOpenApi.builder()
                .group("1, 게시글 관리")
                .packagesToScan(path)
                .build();
    }


    //추가로, ㅋ a오늘은 api만드는과정 복습 꼭해야함!


    @Bean
    public GroupedOpenApi secondOpenApi(){
        String[] path={
                ""  //패스경로지정.해주고
        };


        return GroupedOpenApi.builder()
                .group("2, d미정")
                .packagesToScan(path)
                .build();
    }
}

//하면 뭐가나올지 봐보자..