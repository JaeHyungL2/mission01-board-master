package org.ohgiraffers.board.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor //모든변수포함?

@NoArgsConstructor //기본생성자
public class ReadPostResponse {

    private Long postId;  //롱타입으로 포스트아이디 넘겨주고
    private String title;
    private String content;

    //넘겨주기위해 이안을채워줘야 객체를
}

//여기서 자동으로 어캐하지 ㅋ 알트+엔터이가
