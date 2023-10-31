package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.KeyWordService;
import com.meeting_site_project.YM.vo.KeyWordDTO;
import com.meeting_site_project.YM.vo.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class KeyWordController {

    KeyWordService keyWordService;

    @Autowired
    public KeyWordController(KeyWordService keyWordService) {
        this.keyWordService = keyWordService;
    }

    @GetMapping("/manager/keyWordList")
    public String keyWordListForm(Model model) {

        // 주 키워드와 보조 키워드를 별도의 목록으로 관리합니다.
        List<String> firstKeyWords = new ArrayList<>();

        firstKeyWords = keyWordService.selectFirstKeyWords();

        model.addAttribute("firstKeyWords",firstKeyWords);

        return "/manager/keyWordList";
    }

    @PostMapping("/manager/keyWordList")
    public void insertFirstKeyWord(@RequestBody String firstKeyWord) {
//        keyWordService.insertFirstKeyWord(firstKeyWord);



    }

    @GetMapping("/manager/showSubKeyWords")
    public ResponseEntity<List<String>> showSubKeyWords(@RequestParam("firstKeyword") String firstKeyword) {

        List<String> subKeywords = keyWordService.selectSecondKeyWordsByFirstKeyWord(firstKeyword);

        return ResponseEntity.ok(subKeywords);
    }

    @PostMapping("/manager/addKeyWord")
    public ResponseEntity<String> insertKeyWord(@RequestBody Keyword keyword) {

        if (isDuplicate(keyword)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate keyword");
        }

        keyWordService.insertKeyWord(keyword);

        return ResponseEntity.ok("success");
    }

    private boolean isDuplicate(Keyword keyword) {
        // 여기에서 중복 확인 로직을 구현하세요.
        // 예를 들어, 데이터베이스에서 중복을 확인하거나 다른 비즈니스 로직을 수행할 수 있습니다.
        // 중복인 경우 true를 반환하고, 중복이 아닌 경우 false를 반환하세요.
        // 이 예시에서는 중복 확인을 위한 로직을 더 추가해야 합니다.

        String secondKeyWord = keyWordService.selectKeyWordBySecondKeyWord(keyword.getSecondKeyword());

        if (secondKeyWord == null) { // 중복되는값이 없는 경우
            return false;
        }

        return true;
    }

    @GetMapping("/manager/firstKeywordDelete/{firstKeyWord}")
    public String deleteFirstKeyWord(@PathVariable("firstKeyWord") String firstKeyWord) {

        keyWordService.deleteKeyWordByFirstKeyWord(firstKeyWord); // 주 키워드 , 보조 키워드 함께 삭제

        return "redirect:/manager/keyWordList";
    }

    @DeleteMapping("/manager/deleteSecondKeyWord")
    public ResponseEntity<String> deleteSecondKeyWord(@RequestBody Map<String, String> request) {
        String secondKeyword = request.get("subKeyword");
        System.out.println("secondKeyword = " + secondKeyword);
        boolean result = keyWordService.deleteKeyWordBySecondKeyWord(secondKeyword);

        // 서버에서 해당 데이터를 삭제하고 성공 여부를 클라이언트에 반환
        if (result) {
            return ResponseEntity.ok("deleted");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }
}
