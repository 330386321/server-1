package com.lawu.eshop.framework.web.doc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.framework.web.doc.dto.ApiDocumentVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * API生成
 * 
 * @author Sunny
 * @date 2017/03/31
 */
@Controller
public class DocumentController extends BaseController{

	/**
	 * 显示接口列表
	 * 
	 * @param isAudit 是否通过审核
	 * @param map
	 * @return
	 * @author Sunny
	 */
    @RequestMapping(value = "document.html")
    public String api(Boolean isAudit, ModelMap map) {
    	
    	List<ApiDocumentVO> voList = new ArrayList<ApiDocumentVO>();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	
    	RequestMappingInfo info = null;
    	HandlerMethod method = null;
    	
    	// 注解
    	Api api = null;
    	Audit audit = null;
    	ApiOperation apiOperation = null;
    	
    	try {
	    	WebApplicationContext wc = getWebApplicationContext(getRequest().getSession().getServletContext());  
	        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);  
	        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = rmhp.getHandlerMethods();  
	        for (Iterator<RequestMappingInfo> iterator = handlerMethodMap.keySet().iterator(); iterator.hasNext();) {    
	            info = iterator.next();
	            
	            method = handlerMethodMap.get(info);
	            apiOperation = method.getMethodAnnotation(ApiOperation.class);
	            audit = method.getMethodAnnotation(Audit.class);
	            
	            if ((method.getMethodAnnotation(Audit.class) != null && isAudit) || (!isAudit && audit == null && apiOperation != null)) {
	            	
	            	api = method.getBeanType().getAnnotation(Api.class);
	            	
	            	// 组装VO对象
	            	ApiDocumentVO vo = new ApiDocumentVO();
	            	if (api != null && api.tags() != null) {
	            		vo.setApiName(Arrays.toString(api.tags()));
	            	} else {
	            		//如果Api注解为空,或者tags为空，设置ApiName为空串
	            		vo.setApiName("");
	            	}
	            	
	            	if (audit != null) {
		            	vo.setDate(df.parse(audit.date()));
		            	vo.setReviewer(audit.reviewer());
	            	}
	            	vo.setPath(info.getPatternsCondition().toString());
	            	if (apiOperation != null) {
		            	vo.setName(apiOperation.value());
		            	vo.setNotes(apiOperation.notes());
		            	vo.setHttpMethod(apiOperation.httpMethod());
	            	}
	            	voList.add(vo);
	            	
	            }
	            
	        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	// 按照日期和接口排序
    	Collections.sort(voList, new Comparator <ApiDocumentVO>() {
			@Override
			public int compare(ApiDocumentVO o1, ApiDocumentVO o2){
				
				// 先按照时间排序
				int compare = 0;
				if (o2.getDate() != null && o1.getDate() != null) {
					compare = o2.getDate().compareTo(o1.getDate());
				}
				
				// 如果时间相等的话再按照接口排序
				if (compare == 0) {
					return o2.getApiName().compareTo(o1.getApiName());
				}
				
				return compare;
			}
		});
    	
    	map.addAttribute("voList", voList);
    	return "document";
    }

    public WebApplicationContext getWebApplicationContext(ServletContext sc) {  
        return WebApplicationContextUtils.getRequiredWebApplicationContext(sc);  
    }  

}
