package com.meeting_site_project.YM.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageDTO {
    private int startPage;
    private int endPage;
    private boolean prev, next;
    private int total;
    private Criteria cri;
    private int realEnd;

    public PageDTO(Criteria _criteria, int _total) {
        this.cri = _criteria;
        this.total = _total;
        this.endPage = (int) (Math.ceil(_criteria.getPageNum() / 10.0)) * 10;
        this.startPage = this.endPage - 9;
        this.realEnd = (int) (Math.ceil((_total * 1.0) / _criteria.getAmount()));
        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }
        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}