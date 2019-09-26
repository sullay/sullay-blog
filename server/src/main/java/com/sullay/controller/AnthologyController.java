package com.sullay.controller;

import org.springframework.beans.BeanUtils;
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
import com.sullay.model.temp.AnthologyTemp;
import com.sullay.service.AnthologyService;
import com.sullay.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/anthology")
public class AnthologyController {
	@Autowired
	AnthologyService anthologyService;
	@Autowired
	UserService userService;

	@PostMapping("/create")
	public Msg create(@RequestBody AnthologyTemp anthologyTemp) {
		try {
			if (userService.findByPassWord(anthologyTemp.getPassWord()) == null) {
				throw new Exception("没有操作权限");
			}
			Anthology anthology = new Anthology();
			BeanUtils.copyProperties(anthologyTemp, anthology);
			anthologyService.create(anthology);
			return Msg.success();
		} catch (Exception e) {
			// TODO: handle exception
			return Msg.fail().add("errTip", "创建或更新文集失败").add("errInfo", e.toString());
		}
	}

	@PostMapping("/delete")
	public Msg delete(@RequestBody AnthologyTemp anthologyTemp) {
		try {
			if (userService.findByPassWord(anthologyTemp.getPassWord()) == null) {
				throw new Exception("没有操作权限");
			}
			Anthology anthology = new Anthology();
			BeanUtils.copyProperties(anthologyTemp, anthology);
			anthologyService.delete(anthologyTemp);
			return Msg.success();
		} catch (Exception e) {
			// TODO: handle exception
			return Msg.fail().add("errTip", "删除文集失败").add("errInfo", e.toString());
		}
	}

	@GetMapping("/findPage")
	public Msg findPage(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		try {
			Page<Anthology> anthologyPage = anthologyService.findPage(page, size);
			return Msg.success().add("anthologyPage", anthologyPage);
		} catch (Exception e) {
			// TODO: handle exception
			return Msg.fail().add("errTip", "查询文集列表失败").add("errInfo", e.toString());
		}
	}
}
