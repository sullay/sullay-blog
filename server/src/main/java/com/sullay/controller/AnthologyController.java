package com.sullay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sullay.model.Anthology;
import com.sullay.model.Msg;
import com.sullay.service.AnthologyService;

@CrossOrigin
@RestController
@RequestMapping("/anthology")
public class AnthologyController {
	@Autowired
	  AnthologyService anthologyService;
	  @PostMapping("/create")
	  public Msg create(@RequestBody Anthology anthology) {
		  System.out.println(anthology.toString());
		  anthologyService.create(anthology);
		  return Msg.success();
	  }
	  @PostMapping("/delete")
	  public Msg delete(@RequestBody Anthology anthology) {
		  anthologyService.delete(anthology);
		  return Msg.success();
	  }
	  @GetMapping("/findPage")
	  public Msg findPage(@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="size",defaultValue="10") int size){
		  Page<Anthology> anthologyPage = anthologyService.findPage(page, size);
		  return Msg.success().add("anthologyPage", anthologyPage);
	  }
}
