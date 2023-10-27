package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.CommentMapper;
import com.meeting_site_project.YM.vo.CommentAsk;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository
public class CommentRepository {

    CommentMapper commentMapper;

    @Autowired
    public CommentRepository(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }



    public void insertCommentAsk(CommentAsk commentAsk) {
        commentMapper.insertCommentAsk(commentAsk);
    }

    public List<CommentAsk> selectCommentAsk(String askId) {
        return commentMapper.selectCommentAsk(askId);
    }
}
