package org.ohgiraffers.board.domain.entity;


/* Entity
*  실제 세계의 객체나 개념을 소프트웨어내에서 모델링 한 것으로,
* , 데이터베이스의 테이블에 해당하는 클래스이다.
* post엔티티만들어보자, 추가해보자
*
* ★ '@Data' -> getter, setter, toString ,equalsnade.. 를 묶은건데, 너무많아서 코드가 보기안좋아서
*   한번에 추가할수있는애를 만든거..ㅋ
*   근데, entity에선 setter사용안함 ㅋ 지향해야함..근ㄷㅔ 옛날건 있네..
*   왜 안하냐... 데이터의 일관성을위해..
* setter: private니, 직접접근위한거지. 근데 정보들이 달라질수있으니, 일관성유지로인해 세터 잘안함.ㅋ
* 아하..
*
* dto를 만드는데, (데이터전송하는 ㅋ)  이땐 게터세터 많이사용한다.
*
*
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;

//@Entity 어노테이션은: jpa entity라는 것을 의미. 그래서  id와 @GeneratedValue를 이용해 고유 식별자를 정의함.
//알트엔터는 자동생성. ㅋ 인텔리에서지원하는.
@Entity
//@Builder : 빌더 디자인패턴을 자동으로 생성해준다.
//빌더 디자인패턴은 클래스만들어서 예를들어보자.
@Builder
//@Getter: 클래스의 모든 필드에 대한 게터메소드를 자동으로 생성해준다.
//객체만들면 항상 게터세터 만들었지. ㅋ post안의 필드값을ㅇ 조회할수있게해주는 것인데.ㅋ private니..

@Getter
//@AllArgsConstructor: 클래스의 모든 필드를 매개변수로 받는 전체 생성자를 자동생성한다.
// 수동으로 만들던 생성자를 만들어주는 어노테이션임 ㅋ 빨간줄뜨는건 이미있는데 또만들어서 ㅋ

@AllArgsConstructor

//@NoArgsConstructor: 매개변수가 없는 기본 생성자를 생성하는걸로
// accessLevel을 통해 접근수준 지정할 수 있다. ↓
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue  //개체생성시 순차적으로 뭐?
    private Long postid;
    //인트값 사용했다가 초과해버려서 ㅋ long은 초과못함.ㅋ 쿠팡에서 시스템터진사건이있음 ㅋ
    //간단한 int vs long 으로 ㅋ 그ㅐㄹ서 id는 넉넉하게해줘야..ㅋ int도 똑같이 동작함.
    //아하..


    private String title;
    private String content;
//eneity만들면 post가 빨간줄나옴 ㅋ 왜?, alt+enter로 ㅁ나들어야..

    //게터세터 추가했는데,
    //세터는: 개체 안의 값을 바꾸는것. 어디서든 바꿀수있다. entity가 바뀔수있으니 위험.

    public void update(String title, String content){
        this.title=title;
        this.content=content;
                //해당포스트의 타이틀을 받아오는
    } //라는 메소드 하나만들어주고
    // ,이렇게 업데이트메소드만들어주고,



}
