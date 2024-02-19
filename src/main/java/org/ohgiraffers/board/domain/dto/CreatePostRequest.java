package org.ohgiraffers.board.domain.dto;


//리퀘스트는 사용자에게 입력받는거

//타이틀, 컨텐츠 받아올수잇겟지

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private String title;
    private String content;

    //리퀘스트는 타이틀 컨텐츠 담아서
}
