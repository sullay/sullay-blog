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
		  try {
			  anthologyService.create(anthology);
			  return Msg.success();
		  } catch (Exception e) {
			// TODO: handle exception
			  return Msg.fail().add("errTip", "创建或更新文集失败").add("errInfo", e.toString());
		  }
	  }
	  @PostMapping("/delete")
	  public Msg delete(@RequestBody Anthology anthology) {
		  try {
			  anthologyService.delete(anthology);
			  return Msg.success();
		  } catch (Exception e) {
			// TODO: handle exception
			  return Msg.fail().add("errTip", "删除文集失败").add("errInfo", e.toString());
		  }
	  }
	  @GetMapping("/findPage")
	  public Msg findPage(@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="size",defaultValue="10") int size){
		  try {
			  Page<Anthology> anthologyPage = anthologyService.findPage(page, size);
			  return Msg.success().add("data", anthologyPage);
		  } catch (Exception e) {
			// TODO: handle exception
			  return Msg.fail().add("errTip", "查询文集列表失败").add("errInfo", e.toString());
		  }
	  }
}
