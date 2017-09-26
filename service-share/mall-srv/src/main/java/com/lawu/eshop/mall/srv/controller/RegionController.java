package com.lawu.eshop.mall.srv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawu.eshop.mall.srv.bo.RegionSelectorDataBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.RegionLevelEnum;
import com.lawu.eshop.mall.dto.RegionCityDTO;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.mall.dto.RegionPathDTO;
import com.lawu.eshop.mall.dto.RegionProvinceDTO;
import com.lawu.eshop.mall.srv.bo.RegionBO;
import com.lawu.eshop.mall.srv.converter.RegionConverter;
import com.lawu.eshop.mall.srv.service.RegionService;
import com.lawu.eshop.utils.LonLatUtil;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@RestController
@RequestMapping(value = "region/")
public class RegionController extends BaseController {

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "getRegionList", method = RequestMethod.GET)
    public Result<List<RegionDTO>> getRegionList() {
        List<RegionBO> regionBOS = regionService.getRegionList();
        if (regionBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<RegionDTO> list = new ArrayList<>();
        for (RegionBO regionBO : regionBOS) {
            RegionDTO regionDTO = RegionConverter.coverDTO(regionBO);
            list.add(regionDTO);
        }
        return successGet(list);
    }
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List<RegionPathDTO>> list() {
    	List<RegionBO> regionBOS = regionService.getRegionList();
        if (regionBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<RegionPathDTO> list = new ArrayList<>();
        for (RegionBO regionBO : regionBOS) {
        	RegionPathDTO regionDTO = RegionConverter.coverRegionPathDTO(regionBO);
            list.add(regionDTO);
        }
        return successGet(list);
    }
    
    @RequestMapping(value = "group", method = RequestMethod.GET)
    public Result<List<RegionProvinceDTO>> group() {
        List<RegionBO> regionBOS = regionService.getRegionList();
        if (regionBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        
        // 一级
        Map<Integer, RegionProvinceDTO> region1 = new LinkedHashMap<>();
        // 二级
        Map<Integer, List<RegionCityDTO>> region2 = new LinkedHashMap<>();
        // 三级
        Map<Integer, List<RegionPathDTO>> region3 = new LinkedHashMap<>();
        for (RegionBO regionBO : regionBOS) {
            if (regionBO.getLevelEnum().equals(RegionLevelEnum.REGION_LEVEL_ONE)) {
            	RegionProvinceDTO regionProvinceDTO = RegionConverter.coverRegionProvinceDTO(regionBO);
            	region1.put(regionBO.getId(), regionProvinceDTO);
            } else if (regionBO.getLevelEnum().equals(RegionLevelEnum.REGION_LEVEL_TWO)) {
            	if (region2.get(regionBO.getParentId()) == null) {
            		region2.put(regionBO.getParentId(), new ArrayList<>());
            	}
            	RegionCityDTO regionCityDTO = RegionConverter.coverRegionCityDTO(regionBO);
            	region2.get(regionBO.getParentId()).add(regionCityDTO);
            } else if (regionBO.getLevelEnum().equals(RegionLevelEnum.REGION_LEVEL_THREE)) {
            	if (region3.get(regionBO.getParentId()) == null) {
            		region3.put(regionBO.getParentId(), new ArrayList<>());
            	}
            	RegionPathDTO regionPathDTO = RegionConverter.coverRegionProvinceDTO(regionBO);
            	region3.get(regionBO.getParentId()).add(regionPathDTO);
            }
        }
        
        List<RegionProvinceDTO> rtn = new ArrayList<>();
        for (Map.Entry<Integer, RegionProvinceDTO> entry : region1.entrySet()) {
        	RegionProvinceDTO regionProvinceDTO = entry.getValue();
        	List<RegionCityDTO> regionCityDTOList = region2.get(regionProvinceDTO.getId());
        	for (RegionCityDTO regionCityDTO : regionCityDTOList) {
        		List<RegionPathDTO> regionPathDTOList = region3.get(regionCityDTO.getId());
        		regionCityDTO.setArea(regionPathDTOList);
        	}
        	regionProvinceDTO.setCity(regionCityDTOList);
        	rtn.add(regionProvinceDTO);
        }
        return successGet(rtn);
    }

    /**
     * 根据区域ID查询区域完整名称
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getRegionFullName/{id}", method = RequestMethod.GET)
    public Result<String> getRegionFullName(@PathVariable Integer id) {
        return successGet(regionService.getRegionFullName(id));
    }

    /**
     * 根据区域路径查询区域名称
     *
     * @param regionPath
     * @return
     */
    @RequestMapping(value = "getAreaName", method = RequestMethod.GET)
    public Result<String> getAreaName(@RequestParam String regionPath) {
        String areaPath = regionService.getAreaName(regionPath);
        return successGet(areaPath);
    }

    /**
     * 查询城市列表
     *
     * @return
     */
    @RequestMapping(value = "getRegionLevelTwo", method = RequestMethod.GET)
    public Result<List<RegionDTO>> getRegionLevelTwo() {
        List<RegionDTO> regionDTOS = new ArrayList<>();
        List<RegionBO> regionBOS = regionService.getRegionLevelTwo();
        if (regionBOS.isEmpty()) {
            return successGet(regionDTOS);
        }
        for (RegionBO regionBO : regionBOS) {
            RegionDTO regionDTO = RegionConverter.coverDTO(regionBO);
            regionDTOS.add(regionDTO);
        }
        return successGet(regionDTOS);
    }

    /**
     * 根据区域ID查询区域信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getRegion/{id}", method = RequestMethod.GET)
    public Result<RegionDTO> getRegion(@PathVariable Integer id) {
        RegionBO regionBO = regionService.getRegionById(id);
        return successGet(RegionConverter.coverDTO(regionBO));
    }

    /**
     * 更新区域表经纬度
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateRegionLonLat", method = RequestMethod.GET)
    public Result updateRegionLonLat() {
        List<RegionBO> regionBOS = regionService.getRegionLevelTwo();
        if (!regionBOS.isEmpty()) {
            for (RegionBO regionBO : regionBOS) {
                Map<String, String> map = LonLatUtil.getLonLat(regionBO.getName());
                if (map != null) {
                    BigDecimal lon = new BigDecimal(map.get("lng"));
                    BigDecimal lat = new BigDecimal(map.get("lat"));
                    regionService.updateRegionLonLat(regionBO.getId(), lon.setScale(7, BigDecimal.ROUND_HALF_UP), lat.setScale(7, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        return successGet();
    }

    @RequestMapping(value = "getRegionSelectorData", method = RequestMethod.GET)
    public Result<String> getRegionSelectorData() {

        Map<Object, Object> allProMap = new HashMap<Object, Object>();
        Map<Object, Object> allMap = new HashMap<Object, Object>();
        //start 地区选择器
        List<RegionSelectorDataBO> proviceDataList = regionService.getRegionByParentId("0");
        List<Map<String, Object>> proviceMapList1 = new ArrayList<Map<String,Object>>();//A-G
        List<Map<String, Object>> proviceMapList2 = new ArrayList<Map<String,Object>>();//H-K
        List<Map<String, Object>> proviceMapList3 = new ArrayList<Map<String,Object>>();//L-S
        List<Map<String, Object>> proviceMapList4 = new ArrayList<Map<String,Object>>();//T-Z

        String []a_g = {"安徽省","北京市","重庆市","福建省","甘肃省","广东省","广西壮族自治区","贵州省","澳门"};
        String []h_k = {"海南省","河北省","黑龙江省","河南省","湖北省","湖南省","江苏省","江西省","吉林省"};
        String []l_s = {"辽宁省","内蒙古自治区","宁夏回族自治区","青海省","山东省","上海市","山西省","陕西省","四川省"};
        String []t_z = {"天津市","新疆维吾尔自治区","西藏自治区","云南省","浙江省","台湾","香港"};
        int i = 10;
        for(RegionSelectorDataBO p : proviceDataList){
            String proviceId = p.getCode() == null ? "" :  p.getCode();
            String proviceName = p.getAddress() == null ? "" :  p.getAddress();
            String putProviceId = proviceId;//json显示
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(Arrays.asList(a_g).contains(proviceName)){
                dataMap.put("code", putProviceId);
                dataMap.put("address", proviceName);
                proviceMapList1.add(dataMap);
            }else if(Arrays.asList(h_k).contains(proviceName)){
                dataMap.put("code", putProviceId);
                dataMap.put("address", proviceName);
                proviceMapList2.add(dataMap);
            }else if(Arrays.asList(l_s).contains(proviceName)){
                dataMap.put("code", putProviceId);
                dataMap.put("address", proviceName);
                proviceMapList3.add(dataMap);
            }else if(Arrays.asList(t_z).contains(proviceName)){
                dataMap.put("code", putProviceId);
                dataMap.put("address", proviceName);
                proviceMapList4.add(dataMap);
            }

            List<RegionSelectorDataBO> cityDataList = regionService.getRegionByParentId(proviceId);
            Map<String, Object> cityMap = new HashMap<String, Object>();
            for(RegionSelectorDataBO c : cityDataList){
                String cityId = c.getCode() == null ? "" :  c.getCode();
                String cityName = c.getAddress() == null ? "" : c.getAddress();
                String putCityId = cityId;//json显示
                cityMap.put(putCityId, cityName);

                List<RegionSelectorDataBO> areaDataList = regionService.getRegionByParentId(cityId);
                if(areaDataList==null||areaDataList.isEmpty()||areaDataList.size()<1){
                    System.out.println("---->"+cityId);
                }
                Map<String, Object> areaMap = new HashMap<String, Object>();
                for(RegionSelectorDataBO a : areaDataList){
                    String areaId = a.getCode() == null ? "" : a.getCode();
                    String areaName = a.getAddress() == null ? "" : a.getAddress();
                    String putAreaId = areaId;////json显示
                    areaMap.put(putAreaId, areaName);
                }
                allMap.put(putCityId,areaMap);//区
            }
            allMap.put(putProviceId,cityMap);//市
            i++;
        }

        Map<String, Object> pMap = new HashMap<String, Object>();

        String params = "";
        String paramsa_g = "";
        String paramsh_k = "";
        String paramsl_s = "";
        String paramst_z = "";

        ObjectMapper mapper = new ObjectMapper();
        String bak = "";
        try {

            pMap.put("A-G", proviceMapList1);
            paramsa_g = mapper.writeValueAsString(pMap);
            pMap.clear();

            pMap.put("H-K", proviceMapList2);
            paramsh_k = mapper.writeValueAsString(pMap);
            pMap.clear();

            pMap.put("L-S", proviceMapList3);
            paramsl_s = mapper.writeValueAsString(pMap);
            pMap.clear();

            pMap.put("T-Z", proviceMapList4);
            paramst_z = mapper.writeValueAsString(pMap);
            pMap.clear();

            params = mapper.writeValueAsString(allMap);

            bak = "{\"86\":{"+paramsa_g.substring(1, paramsa_g.length()-1)+","+
                    paramsh_k.substring(1, paramsh_k.length()-1)+","+
                    paramsl_s.substring(1, paramsl_s.length()-1)+","+
                    paramst_z.substring(1, paramst_z.length()-1)+"},"+params.substring(1);
            System.out.println(bak);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return successCreated(bak);
    }
}
