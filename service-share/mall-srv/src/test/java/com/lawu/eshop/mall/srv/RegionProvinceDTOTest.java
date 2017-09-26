package com.lawu.eshop.mall.srv;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.mall.dto.RegionProvinceDTO;

public class RegionProvinceDTOTest {
	
	@Ignore
	@Test
	public void convert() throws IOException{
		String regionJson = FileUtils.readFileToString(new File("C:/Users/Administrator/Desktop/address.json"), "UTF-8");
		List<RegionProvinceDTO> regionList = JSONObject.parseArray(regionJson, RegionProvinceDTO.class);
		System.out.println(regionList);
	}
	
}
