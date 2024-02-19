package org.ohgiraffers.board.service;


//서비스계층이란걸 설명해주는 어노테이션

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ohgiraffers.board.domain.dto.*;
import org.ohgiraffers.board.domain.entity.Post;
import org.ohgiraffers.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/** service 인터페이스와 서비스를 인터페이스와 구현체로 나누는 이유
 * 1. 다형성과 OCP원칙을 지키기 위해서
 * 인터페이스와 구현체가 나누어지면, 구현체는 외부로부터 독립되어, 구현체의 수정이나 확장이 자유로워지고,
 * (ex) 서비스만 바꿀시, 다른 레이어(레포지토리나) 영향을 안준다.
 * 2. 관습화된 추상화방식.
 * 과거 Spring에서 AOP를 구현할 때, jdk dynamic proxy를 사용했는데, 이땐 인터페이스가 꼭 필요했었음.
 * 그래서, 인터페이스로 나눠서 사용하고있었던 이유이기도하고, CGLB를 기본적으로 포함하여 클래스기반으로 프록시 객체를 생성할 수 있게 되었다.
 *  프록시를 클래스자체에서 만들수있음 스프링2.0ㅇ이상버전부턴, 그래서 밑에 가능했음.
 *  ↓
 * 큰기업에선, 인터페이스로 나눠서 독ㄷ립시켜서, ex)패스워드 비번변경시, 다른기능 추가해서 변경할수도있으니,
 * 서비스로직이 달라질수잇읜 추상화해서 하나의 서비스가 아닌, 이걸 구현하는 방법으로 나누느것.
 * 서비스에서 인터페이스 구현체 나눌ㄹ땐, 추가할생각이 있는지 고려해서, 추가나변경이 많이될거같으면 인터페이스로 나눠서 구현하는게맞다.
 * or 수정될 일이 없으면 굳이 나누지 않아도된다.
 */
@Service
@Transactional(readOnly = true) //이 어노테이션 썼는데 잠시설명,ㅋ
//readonly = true 이 상태는,  뭐 없는거랑 비슷한상태,   트랜잭션이 모든 데이터베이스연산을 하나로묶어 처리하는데,
// 선언하지 않은것도 비슷한 상태로봄..ㅋ  왜 해놓냐면, ex) 조회시 트랜잭션은 쓸필요없지만, crud 할땐 트랜잭션써서 관리해야해서 어쩔수없이 반만쓰기위해서군 ㅋ
//메서드 로직짜다보면, 은행에서 돈입금 ->   중간과정에서 상대방이 받아서 저장하는 로직이 작동안하면, 내돈은 사라지고 돈은안보내지면
        //이럴때 트랜잭션으로관리하면 하나라도 작동안하면 rollback! 없던일로 만듬 ㅋ 그래서 트랜잭션사용함 ㅋ

//아하..  그래서 데이터변경시 트랜잭셔널 어노테이션써서 트랜잭션으로 관리하도록 지정해줘야함.!!!


/** @Transactional
 *
 * 선언적으로 트랜젝션 관리를 가능하게 해준다.
 * 선언된 메서드가 실행되는 동안, 모든 데이터베이스 연산을 하나의 트랜잭션으로 묶어 처리한다.
 * 이를통해, 할 수 있는건 메서드 내에서 데이터베이스 상태를 변경하는 작업들이 모두 성공적으로 완료되면 그 변경사항을
 * 커밋하고, 하나라도 실패하면 모든 변경사항을 rollback 시켜 관리한다.
 *
 *
 * 트랜잭션이란? (Transaction)
 * 간단하게 말하면, 데이터베이스의 상태를 변화시키기위해, 수행하는 작업의 단위다.
 *
  */
//서비스는 레포지토리랑 연결해야하니,
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        //리퀘스트받ㄷ으면 사용자가 작성한 타이틀과 컨텐츠가있는데,
        //이때 를위해 객체만들어주자

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        // 포스트객체로 사용자에게 받아온걸로  객체만들어줫으니, 우린 이 걸로 저장해야지?

        postRepository.save(post);  //이렇게 저장해줄거임. ㅋ save메소드는 리턴으로 저장한값 돌려주거든?
        Post savePost = postRepository.save(post); //로 돌려준값 저장받고  , savepost는 데이터베이스에 저자오딘애들

        return new CreatePostResponse(savePost.getPostid(), savePost.getTitle(), savePost.getContent());


    }

    //여기선 그럼 트랜잭션 필요없을까?  조회만하는거면 필요없지 ㅋ

    // crud에서 필요 ㅋ 롤백해야하니 하나라도 아다리안맞으면.

    public ReadPostResponse readPostById(Long postId) {   //리턴타입이 리스폰스로 , 롱타입으로 포스트아이디 받아오고
        Post fountPost=postRepository.findById(postId) // 이렇게 메소드완성시키면 빨간줄뜸..왜냐, 있을지 없을지 몰라서 ㅋ
        //파인드바이아이디는 없을경우를 예외처리해줘야 ㅋ

        .orElseThrow(() ->new EntityNotFoundException("해당 postId로 조회된 게시글이 없습니다."));  //예외발생시를 해줌, 없을시 엔티티낫파운드익셉션 발생! :
        //포스트아이디 뭘 입력할지모르니 꼭해줘야지 ㅋ (포스트아이디는 usage눌러보면
        return new ReadPostResponse(fountPost.getPostid(), fountPost.getTitle(), fountPost.getContent());
        //이걸 리턴해줌.
    }

    @Transactional  //db상태 변경되니.
    public UpdatePostResponse updatePost(Long postId, UpdaatePostRequest request) {
        Post fountPost=postRepository.findById(postId)
                .orElseThrow(() ->new EntityNotFoundException("해당 postId로 조회된 게시글이 없습니다."));
        //확인하기위한거고.. 리퀘스트에따라 업데이트해줘야함..

        //Dirth Checking -
        fountPost.update(request.getTitle(), request.getContent());
                //꺼내준다. 이렇게
                //가져온 entity 변경해줬더니,

        //jpa가 감지해서 알아서 바꿔ㅏ줌.ㅋ
        return new UpdatePostResponse(fountPost.getPostid(), fountPost.getTitle(), fountPost.getContent());
                //파운드포스트 변경되었으면, 해당하는 아이디
        //근데 db에있는거 업데이트하는데 뭔가 이상하네?
        //저장을안하네? 그 이유가있다! 왜?
        // -> Dirth Checking  jpa는 더티체킹으로 디비내용을 바꿔줄 수 있다.
        //더티체킹은 하나의 트랜잭션안에 있는걸 받아뒀다 값이 바뀌면 탐지해서 db에서 바꿔줌..

    }
    @Transactional
    public DeletePostResponse deletePost(Long postId){
        Post fountPost=postRepository.findById(postId)
                .orElseThrow(() ->new EntityNotFoundException("해당 postId로 조회된 게시글이 없습니다."));
        postRepository.delete(fountPost);
        return new DeletePostResponse(fountPost.getPostid());
    }

    //이렇게 서비스를 만들어야하네 ㅋㅋㅋ 자동으로가네 ㅋ 필요하니 프레임워크!
    public Page<ReadPostResponse> readAllPost(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);  //포스트레포지토리에서 찾아온다.
        //포스트를 가져올 수 있다.

        return postPage.map(post -> new ReadPostResponse(
                post.getPostid(),
                post.getTitle(),
                post.getContent()
            //잉렇게하면 페이지안에 있는 포스트를  리드포스트 리스폰스 형태로 변환해주면서,
            //아래 값들을 하나씩 꺼내서 변환해준다.
        ));
    }


    //이렇게 서비스메소드해주고 ㅋ너트롤러가서
    //알트+엔터로 생성해주고,
    //메소드 만들어보자! 서비스에..
}

//dlfjgrp tjqltm이렇게 서비스메소드 다만듬


//이렇게 먼저 만들어놔야. 깃에서 패키지 커밋이 유용해짐.
// 이렇게안하면 패키지가 안올라가질수있음 ㅋ
//그래서 팀장은 패키지구조 먼저 만들어야함. -> 빌드하고, -> 설정해야
// 팀원들이 클론받고, example구조로 자기 기능구현맞게 역할분담가ㅡㄴㅇ!
//클래스이름과 패키지명 구분해주자!
