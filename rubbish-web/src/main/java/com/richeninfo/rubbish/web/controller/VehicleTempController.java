/*
 * Author xuliangjun
 * Copyright (c) 2006 - 2017 RICHENINFO All Rights Reserved
 * Powered By [rapid-generator]
 */

package com.richeninfo.rubbish.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.richeninfo.rubbish.entity.model.*;

import com.richeninfo.rubbish.service.VehicleTempService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.itcloudinfo.commons.json.bean.JsonRequest;
import com.itcloudinfo.commons.json.bean.JsonResult;

/**
 * 基于return JSON的spring mvc方法列表
 * <pre>
 * /vehicletemp/query      	=> query()		分页查询
 * /vehicletemp/findById       => findById()	查询单条数据
 * /vehicletemp/saveOrUpdate POST   => saveOrUpdate()	根据ID(识别)保存或者更新数据
 * /vehicletemp/delete	POST    => delete()		单条数据删除
 * /vehicletemp/batchDelete POST  => batchDelete()		批量数据删除
 * </pre>
 *
 *
 * @author xlj email:ahxuliangjun(a)gmail.com
 * @version 1.1.0-SNAPSHOT
 * @since 1.1.0
 * * */
@RestController
@RequestMapping("/vehicletemp")
public class VehicleTempController {
	private static final Logger logger = LoggerFactory.getLogger(VehicleTempController.class);

	@Autowired
	private VehicleTempService vehicletempService;

	/** 分页查询 */
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public JsonResult query(@RequestBody JsonRequest request) {
		Page page = new Page(request.getInteger("page",1),request.getInteger("size",10));
		VehicleTemp vehicletemp = request.getObject(VehicleTemp.class);
		Page<VehicleTemp> pageList = this.vehicletempService.selectPage(page, new EntityWrapper(vehicletemp));
		return JsonResult.newSuccess(pageList);
	}

	/** 显示 */
	@RequestMapping(value="/findById",method=RequestMethod.POST)
	public JsonResult findById(@RequestBody VehicleTemp vehicletemp){
		vehicletemp = this.vehicletempService.selectById(vehicletemp.getId());
		return JsonResult.newSuccess(vehicletemp);
	}
	
	/** 新增 or 更新，根据ID识别 */
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public JsonResult saveOrUpdate(@RequestBody VehicleTemp vehicletemp){
		try {
			this.vehicletempService.insertOrUpdate(vehicletemp);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return JsonResult.newError(e.getMessage());
		}
		return JsonResult.newSuccess(vehicletemp);
	}

	/** 删除 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public JsonResult delete(@RequestBody VehicleTemp vehicletemp) {
		this.vehicletempService.deleteById(vehicletemp.getId());
		return JsonResult.newSuccess("删除成功");
	}

	/** 批量删除 */
	@RequestMapping(value="/batchDelete",method=RequestMethod.POST)
	public JsonResult batchDelete(@RequestBody JsonRequest request) {
		JSONArray items = request.getJSONArray("items");
		for(int i = 0; i < items.size(); i++) {
		this.vehicletempService.deleteById(items.getInteger(i));
		}
		return JsonResult.newSuccess("删除成功");
	}
}

