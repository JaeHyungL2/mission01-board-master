package org.ohgiraffers.board.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {
    private long postId;
    private String title;
    private String content;



}

//글작성시 받아야하는데, 리턴해줘야함. 잘생성되이ㅓㅆ으면,
//그럼 포스트아이디가 생성되는데 이 아이디까지 리턴해주는걸 만들어보자

