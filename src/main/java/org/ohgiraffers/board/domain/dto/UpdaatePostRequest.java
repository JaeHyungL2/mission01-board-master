package org.ohgiraffers.board.domain.dto;


//리퀘스트도 만들어주고

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdaatePostRequest {
    //리퀘스트엔 어떤것들이 변했는지 받아와야하니,

    private String title;
    private String content;

    //어떻게 바뀌는지 알아봐야하니.
}
