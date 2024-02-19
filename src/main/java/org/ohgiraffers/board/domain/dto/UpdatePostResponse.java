package org.ohgiraffers.board.domain.dto;


//얘ㄴ만들어주고 롬복들

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostResponse {

    private Long PostId;
    private String title;
    private String content;


    //업데이트된거니

}

//아~ 이렇게 ㅋ