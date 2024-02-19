package org.ohgiraffers.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ohgiraffers.board.domain.dto.*;
import org.ohgiraffers.board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/** 레이어드 아키텍쳐
 *
 */

/*

' ' 와 " " 차이였군 ㅋ빨간줄

잠깐!
 ㅁ controller vs restcontrloller 차이는?

일반 컨트롤러는 주로: view를 반환하기위해 사용되고(메서드만들고 리턴할때, 화면이 어딘지 경로지정하지? 이걸 화면리턴해주는거라말하고
하지만 종종 컨트롤러를 쓰면서도 데이터를 바노한해야 할 때가 있는데, 이럴때 사용하는 것이 @ResponseBody 어노테이션을 붙여서 데이터 반환하기도함 (jsp현ㅇ식으로 반환하고)
리스펀스바디도 데이터리턴해주니, 컨트롤러에 같이써주는게 vs restcontroller다.

컨트롤러바디와 리스펀트를 합친게 = 레스트컨트롤러.  (포스트컨트롤러에 잇는 데이터 반환하는 api들)


* Rest란?
Representational Astate Transfer의 약자로, 자원을 이름으로 구분하여 자원의 상태를 주고받는 것을 의미하는 개념으로 보면됨.

Rest특징으로는 기본적으로 웹의 기존 기술과 http프로토콜을 그대로 사용하기에, 웹의 장점을 최대한 활용할 수 있는 아키텍쳐 스타일임.

쉽게 설명해서, 컨트롤러는: 화면반환해주는 친구고,   데이터반환하려면 RestController써줌.
    뭐 서버와 프론트가 '타임리프'로구성하면 둘다있을수있는데,
        프론트
,
 */
//컨트롤러도 어노테이션 붙여줘야

    @Tag(name="posts", description = "게시글api")
            //이렇게 스웨거에서 명세 달아주는군 ㅋ
    @Controller
    @ResponseBody
    //없애줘야겠지 두개합친게 vs restcontroller니..ㅋ
    @RequestMapping("/api/v1/posts") //복수형으로써줘야함 ㅋ보통 , 특정url지정해줌.
    //경로지정해주자
    //@RequireArgsConstrucrot는 final이나 @nonnull어노테이션이 붙은 필드에 대한 생성자를 자동생성해줌.
    @RequiredArgsConstructor

public class PostController {
    //컨트롤러로 다시돌아와서,
    // 미션인 게시판기능 CRUD 만들어보자 여기서 ㅋ

    //조건없이 ㅋ 300자이상 뭐 이런거 ㅋ

    //컨트롤러에서 계층을 나누고, 서비스를
    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시글 작성", description = "제목(title), 내용(content) 입력해주세요 ")
        //이렇게 스웨거에서 명세 달아주는군 ㅋ

    public ResponseEntity<CreatePostResponse> postCreate(@RequestBody CreatePostRequest request) {
        CreatePostResponse response = postService.createPost(request);
        //사용자가 요청보낸걸 처리하고, 리스폰스받는것!
        //서비스로 받으면 처리하는
        //서비스에 크리에이트포스트안만들어서 빨간줄뜬거고.
        //리턴타입은 맞춰줘야하니,
        return new ResponseEntity<>(response, HttpStatus.OK);

        //리스폰스엔티티는  메시지담아 보내주자. 보내기만해도성공.
        //일단 null넣어두고 빨간거해결해보자
    }  //ResponseEntity를씀 , 그리고 dto를 만드느데

    //이번엔조회@

    //이럴땐 url설정을  큰따옴표로 " "

    @GetMapping("/{postId}") //포스트아이디에 괄호해놓고하면 패스배리어블 어노테이션으로 받아올수있음 ㅋ body에 안넣고도
    public ResponseEntity<ReadPostResponse> postRead(@PathVariable Long postId) {

        //이제 포스트서비스에서,  받아온id를 래포지토리에 저장된걸 받아오는 로직짜자
        ReadPostResponse response = postService.readPostById(postId);  //넣어줌.
        //이것도 서비스에서 리스폰스로 받아와야하니 ㅋ
        return new ResponseEntity<>(response, HttpStatus.OK);  //반환해주겠지 ㅋ


    }  //dto를 붙여줘야 리스폰스엔티티엔
    //id만 보내니, 이럴땐 패스배리어블씀..

    //업데이트는 putmapping씀

    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> postUpdate(@PathVariable Long postId, @RequestBody UpdaatePostRequest request) {

        //이제 서비스로가는 메소드짜보자

        //포스트id받아온건,  어떤게시글 수정할건지 위해서 받아옴.  그럼 서비스에도 넘겨줘야..
        //업데이트포스트리퀘스트는 어떤걸 바꿀지 알아야해서..

        UpdatePostResponse response = postService.updatePost(postId, request); //리퀘스트바디도받고,   어떤게필요해서 구현하는지 보면서짜야함
        return new ResponseEntity<>(response, HttpStatus.OK); //일단 널로.

        //이제 바뀌는지 안바뀌는지 확인해보자. ㅋ
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<DeletePostResponse> postDelete(@PathVariable Long postId) {
        //만들어주자. 리스폰스
        DeletePostResponse response = postService.deletePost(postId);
        return new ResponseEntity<>(response, HttpStatus.OK); //일단 널로.

        //컨트롤러는 구조가 어느정도비슷 ㅋ

    }

    //리스트도 - 컨트롤러로 와서..  조회면 어떤매핑?  겟매핑! (포스트id받을필요x, 전체를받을거니)
    @GetMapping
    public ResponseEntity<Page<ReadPostResponse>> postReadALL(
            @PageableDefault(size = 5, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable) {

        //페이지처리시, 사이드가 어떤정렬할지 설정해주는 어노테이션!
        Page<ReadPostResponse> responses = postService.readAllPost(pageable);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}  //ddl오토체킹 때문에 업데이트로바꿔줘라 ㅋ create대신.

//이렇게 구조안나누고 하나에 때려박으면, 구현은되도, 역할구현안되서 테스트코드짤때힘들어짐..ㅋ
//코드 재사용도못하고, 유지보수가 힘들어짐.. 아하!..

// 독립적으로 만들어서 역할줘야함 ㅋ" controller너는 외부만, 출력만 등등
//서비스는 비즈니스로직만 담당!하도록하고, 요청오면 요청해결하고
// 레포지토리같은경우 요청처리시, 데이터관리해야해서 관리하는곳이니.
    //계층나눠두고 역할분담함..  이런식으로 나눴군..


//이래서! 이런 구조로 스프링에선 잡혀있다.



//포스트맨에서 작성해줘야.  api/v1/posts , url을.

//여기까지! 이렇게 문법에맞게,(프레임워크) , 결과물 도출해내는 사람들이군..ㅋㅋ  오케이..!