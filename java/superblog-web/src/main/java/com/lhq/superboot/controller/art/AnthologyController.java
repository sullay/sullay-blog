package com.lhq.superboot.controller.art;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.lhq.superboot.domain.Anthology;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.service.AnthologyService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.StringUtils;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/anthology")
public class AnthologyController {

	@Autowired
	AnthologyService anthologyService;

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public Object create(@RequestBody Anthology anthology) {
		if (StringUtils.isEmpty(anthology.getName()) ) {
			throw new SuperBootException("内容不能为空");
		}
		
		anthologyService.create(anthology);
		return "创建成功";
	}
	
	@PostMapping("/update")
	public Object update(@RequestBody Anthology anthology) {
		if (StringUtils.isEmpty(anthology.getId()) ) {
			throw new SuperBootException("Id不能为空");
		}
		if (StringUtils.isEmpty(anthology.getName()) ) {
			throw new SuperBootException("内容不能为空");
		}
		
		anthologyService.update(anthology);
		return "更新成功";
	}

	@PostMapping("/delete")
	public Object delete(@RequestBody Anthology anthology) {
		anthologyService.delete(anthology);
		return "删除成功";
	}

	@GetMapping("/findPage")
	public Object findPage(PageInfo<Anthology> page) {
		Page<Anthology> anthologyPage = anthologyService.findPage(page.getPageNum(), page.getPageSize());
		page.setList(anthologyPage.getResult());
        page.setTotal(anthologyPage.getTotal());
		return page;
	}
}
