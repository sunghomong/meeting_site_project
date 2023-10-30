package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.CommentAsk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CommentMapper {

    public void insertCommentAsk(CommentAsk commentAsk);

    public List<CommentAsk> selectCommentAsk(String askId);
}
