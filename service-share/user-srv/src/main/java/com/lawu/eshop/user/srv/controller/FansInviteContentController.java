package com.lawu.eshop.user.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.constants.FansInviteResultEnum;
import com.lawu.eshop.user.dto.FansInviteContentDTO;
import com.lawu.eshop.user.param.FansInviteContentExtendParam;
import com.lawu.eshop.user.srv.bo.FansInviteContentBO;
import com.lawu.eshop.user.srv.converter.FansInviteContentConverter;
import com.lawu.eshop.user.srv.service.FansInviteContentService;
import com.lawu.eshop.user.srv.service.FansInviteResultService;

@RestController
@RequestMapping(value = "fansInviteContent/")
public class FansInviteContentController extends BaseController {

	@Autowired
	private FansInviteContentService fansInviteContentService;
	
	@Autowired
	private FansInviteResultService fansInviteResultService;
	
	/**
	 * 保存邀请内容
	 * @param param
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveInviteContentService", method = RequestMethod.POST)
    public Result listInviteFans(@RequestBody FansInviteContentExtendParam param) {
    	Long i = fansInviteContentService.saveInviteContentService(param);
		return successCreated((Object)i);
    }
    
    /**
	 * 保存邀请内容
	 * @param param
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveInviteContentExtendService", method = RequestMethod.POST)
    public Result saveInviteContentExtendService(@RequestBody FansInviteContentExtendParam param) {
    	Long i = fansInviteContentService.saveInviteContentExtendService(param);
		return successCreated((Object)i);
    }
    
    /**
     * 查询邀请内容
     * @param id
     * @return
     */
    @RequestMapping(value = "selectInviteContentById/{id}/{relateId}", method = RequestMethod.POST)
    public Result<FansInviteContentDTO> selectInviteContentById(@PathVariable("id") Long id, @PathVariable("relateId") Long relateId) {
    	FansInviteContentBO bo = fansInviteContentService.selectInviteContentById(relateId);
    	FansInviteContentDTO dto = FansInviteContentConverter.converterBoToDto(bo);
    	int i = fansInviteResultService.selectInviteResultById(id);
    	dto.setFansInviteResultEnum(FansInviteResultEnum.getEnum((byte)i));
    	Result<FansInviteContentDTO> result = new Result<FansInviteContentDTO>();
    	result.setModel(dto);
    	return result;
    }
}
