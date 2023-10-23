package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindId {
    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String emailId;
    @NotBlank(message = "이메일 도메인을 입력해주세요.")
    private String emailDomain;
}
