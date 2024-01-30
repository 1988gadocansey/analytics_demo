package com.gad.mathswork.Endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gad.mathswork.Application.MathsWorkRepository;
import com.gad.mathswork.Domain.MathsWork;

import lombok.AllArgsConstructor;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/integers")
@AllArgsConstructor
public class MathsWorkEndpoint {
 @Autowired
   MathsWorkRepository mathsworkRepository;

  @GetMapping("/index")
  public ResponseEntity<Map<String, Object>> getAllIntegers(
     
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size) {

    try {
      List<MathsWork> integers = new ArrayList<MathsWork>();
      Pageable paging = PageRequest.of(page, size);

      Page<MathsWork> pageData;

      pageData = mathsworkRepository.findAll(paging);

      integers = pageData.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("mathwork", integers);
      response.put("currentPage", pageData.getNumber());
      response.put("totalItems", pageData.getTotalElements());
      response.put("totalPages", pageData.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * save mathswork entity
   */

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public MathsWork createMathWork(@RequestBody MathsWork mathwork) {
    return mathsworkRepository.save(mathwork);
  }
}