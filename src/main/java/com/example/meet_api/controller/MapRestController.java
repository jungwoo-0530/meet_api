package com.example.meet_api.controller;

import com.example.meet_api.dto.BaseResponse;
import com.example.meet_api.dto.CommonResponse;
import com.example.meet_api.service.MapService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
@Slf4j
@RequiredArgsConstructor
public class MapRestController {

    private final MapService mapService;

    @GetMapping("/test")
    public ResponseEntity<? extends BaseResponse> test(HttpServletRequest request) {
        return ResponseEntity.ok().body(new CommonResponse<>("test", "test"));
    }

    /*@GetMapping("/boards/{boardId}")
    public ResponseEntity<? extends BaseResponse> readBoard(@PathVariable(name = "boardId") Long boardId,
                                                            HttpServletRequest request) {

//        BoardDto boardDto = BoardDto.builder().
//                content(board.getContent()).
//                title(board.getTitle()).
//                author(board.getMember().getLoginId()).
//                type(board.getType()).
//                email(board.getMember().getEmail()).
//                available(board.isAvailable()).
//                editable(editable).
//                memberImageUri(board.getMember().getImgUri()).
//                updateTime(board.getUpdateDate()).
//                createTime(board.getCreateDate()).
//                build();
//
//        return ResponseEntity.ok().body(new CommonResponse<>(boardDto, "게시물을 불러왔습니다."));
    }*/

}
