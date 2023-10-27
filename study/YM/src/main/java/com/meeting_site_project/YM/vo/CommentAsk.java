package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAsk {
    private String commentId;
    private String userId;
    private String askId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
}
