package cn.sequoiacap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.sequoiacap.utils.MD5Utils;
import cn.sequoiacap.utils.NumericConvertUtils;
import cn.sequoiacap.utils.SnowFlake;
import io.swagger.annotations.ApiOperation;

@ApiOperation(value = "新增用户")
@Controller
@ResponseBody
@RequestMapping("/test")
public class TestController {

	// 保存md5->短域名眏射
	Cache<String, String> md5Cache = CacheUtil.newLFUCache(100);
	// 保存url->md5映射
	Cache<String, String> urlCache = CacheUtil.newLFUCache(100);
	//方便测试用，实际定义在getLong中
	String temp;

	// @RequestMapping("/test")
	@PostMapping("/toShort")
	String toShort(String url) {
		String md5str = MD5Utils.md5(url);
		urlCache.put(md5str, url);
		SnowFlake snowFlake = new SnowFlake(0, 0);
		temp = "http://sequoiacap.cn/" + NumericConvertUtils.toOtherNumberSystem(snowFlake.nextId(), 62);
		md5Cache.put(temp, md5str);
		return temp;
	}

	@PostMapping("/getLong")
	String getLong(String url) {
		url=temp;
		String md5str = md5Cache.get(url);
		return urlCache.get(md5str);
	}
}
