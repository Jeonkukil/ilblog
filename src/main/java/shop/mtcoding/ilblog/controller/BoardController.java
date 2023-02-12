package shop.mtcoding.ilblog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.ilblog.dto.ResponseDto;
import shop.mtcoding.ilblog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.ilblog.handler.ex.CustomApiException;
import shop.mtcoding.ilblog.model.BoardRepository;
import shop.mtcoding.ilblog.model.User;
import shop.mtcoding.ilblog.service.BoardService;

@Controller
public class BoardController {
    

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;




    @GetMapping({ "/", "/board" })
    public String maain(Model model) {
        model.addAttribute("dtos", boardRepository.findAllWithUser());
        return "board/main";
    }

    @GetMapping("/board/{id}")
    public String detail() {
        return "board/detail";
    }


    @PostMapping("/board")
    public @ResponseBody ResponseEntity<?> save(@RequestBody BoardSaveReqDto BoardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        if (BoardSaveReqDto.getTitle() == null || BoardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title을 작성해주세요");
        }
        if (BoardSaveReqDto.getContent() == null || BoardSaveReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content를 작성해주세요");
        }
        if (BoardSaveReqDto.getTitle().length() > 100) {
            throw new CustomApiException("title의 길이가 100자 이하여야 합니다");
        }
        
       boardService.글쓰기(BoardSaveReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "글쓰기성공", null), HttpStatus.CREATED);
    }
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
