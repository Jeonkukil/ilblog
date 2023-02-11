package shop.mtcoding.ilblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.ilblog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.ilblog.handler.ex.CustomApiException;
import shop.mtcoding.ilblog.model.BoardRepository;
import shop.mtcoding.ilblog.util.HtmlParser;

@Transactional(readOnly = true)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {
        String thumbnail = HtmlParser.getThumbnail(boardSaveReqDto.getContent());

        int result = boardRepository.insert(
                boardSaveReqDto.getTitle(),
                boardSaveReqDto.getContent(),
                thumbnail,
                userId);
        if (result != 1) {
            throw new CustomApiException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

   
}
