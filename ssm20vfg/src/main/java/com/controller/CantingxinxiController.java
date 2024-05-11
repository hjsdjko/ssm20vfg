package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.CantingxinxiEntity;
import com.entity.view.CantingxinxiView;

import com.service.CantingxinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 餐厅信息
 * 后端接口
 * @author 
 * @email 
 * @date 2022-05-19 16:00:26
 */
@RestController
@RequestMapping("/cantingxinxi")
public class CantingxinxiController {
    @Autowired
    private CantingxinxiService cantingxinxiService;


    @Autowired
    private StoreupService storeupService;

    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,CantingxinxiEntity cantingxinxi, 
		HttpServletRequest request){

        EntityWrapper<CantingxinxiEntity> ew = new EntityWrapper<CantingxinxiEntity>();
		PageUtils page = cantingxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, cantingxinxi), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,CantingxinxiEntity cantingxinxi, 
		HttpServletRequest request){
        EntityWrapper<CantingxinxiEntity> ew = new EntityWrapper<CantingxinxiEntity>();
		PageUtils page = cantingxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, cantingxinxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CantingxinxiEntity cantingxinxi){
       	EntityWrapper<CantingxinxiEntity> ew = new EntityWrapper<CantingxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( cantingxinxi, "cantingxinxi")); 
        return R.ok().put("data", cantingxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CantingxinxiEntity cantingxinxi){
        EntityWrapper< CantingxinxiEntity> ew = new EntityWrapper< CantingxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( cantingxinxi, "cantingxinxi")); 
		CantingxinxiView cantingxinxiView =  cantingxinxiService.selectView(ew);
		return R.ok("查询餐厅信息成功").put("data", cantingxinxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CantingxinxiEntity cantingxinxi = cantingxinxiService.selectById(id);
		cantingxinxi.setClicknum(cantingxinxi.getClicknum()+1);
		cantingxinxi.setClicktime(new Date());
		cantingxinxiService.updateById(cantingxinxi);
        return R.ok().put("data", cantingxinxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CantingxinxiEntity cantingxinxi = cantingxinxiService.selectById(id);
		cantingxinxi.setClicknum(cantingxinxi.getClicknum()+1);
		cantingxinxi.setClicktime(new Date());
		cantingxinxiService.updateById(cantingxinxi);
        return R.ok().put("data", cantingxinxi);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R thumbsup(@PathVariable("id") String id,String type){
        CantingxinxiEntity cantingxinxi = cantingxinxiService.selectById(id);
        if(type.equals("1")) {
        	cantingxinxi.setThumbsupnum(cantingxinxi.getThumbsupnum()+1);
        } else {
        	cantingxinxi.setCrazilynum(cantingxinxi.getCrazilynum()+1);
        }
        cantingxinxiService.updateById(cantingxinxi);
        return R.ok();
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CantingxinxiEntity cantingxinxi, HttpServletRequest request){
    	cantingxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(cantingxinxi);

        cantingxinxiService.insert(cantingxinxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CantingxinxiEntity cantingxinxi, HttpServletRequest request){
    	cantingxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(cantingxinxi);

        cantingxinxiService.insert(cantingxinxi);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CantingxinxiEntity cantingxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(cantingxinxi);
        cantingxinxiService.updateById(cantingxinxi);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        cantingxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<CantingxinxiEntity> wrapper = new EntityWrapper<CantingxinxiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = cantingxinxiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,CantingxinxiEntity cantingxinxi, HttpServletRequest request,String pre){
        EntityWrapper<CantingxinxiEntity> ew = new EntityWrapper<CantingxinxiEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        
        params.put("order", "desc");
		PageUtils page = cantingxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, cantingxinxi), params), params));
        return R.ok().put("data", page);
    }







}
