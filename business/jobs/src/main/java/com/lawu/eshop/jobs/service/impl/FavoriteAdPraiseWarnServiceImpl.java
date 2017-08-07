package com.lawu.eshop.jobs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.dto.FavoriteAdPraiseWarnDTO;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.FavoriteAdPraiseWarnService;
import com.lawu.eshop.jobs.service.FavoriteAdService;
import com.lawu.eshop.jobs.service.MessageService;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
@Service
public class FavoriteAdPraiseWarnServiceImpl extends BaseController implements FavoriteAdPraiseWarnService {
	
	@Autowired 
	private FavoriteAdService favoriteAdService;
	
	@Autowired
    private MessageService messageService;

	@Override
	public void favoriteAdPraiseWarn() {
		
		 Result<List<FavoriteAdPraiseWarnDTO>>  result = favoriteAdService.selectFavoriteAdPraise();
		 
		 if(!isSuccess(result)){
			 return ;
		 }
		 
		 List<FavoriteAdPraiseWarnDTO> list= result.getModel();
		 
		 for (FavoriteAdPraiseWarnDTO favoriteAdPraiseWarnDTO : list) {
			
			MessageInfoParam messageInfoParam = new MessageInfoParam();
			MessageTempParam messageTempParam = new MessageTempParam();
			messageTempParam.setAdName(favoriteAdPraiseWarnDTO.getTitle());
			messageTempParam.setAdTypeName("抢赞");
			messageInfoParam.setRelateId(favoriteAdPraiseWarnDTO.getAdId());
			messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_AD_PRAISE_NOTICE);
			messageInfoParam.setMessageParam(messageTempParam);
			messageService.saveMessage(favoriteAdPraiseWarnDTO.getMemberNum(), messageInfoParam); 
		    
		    favoriteAdService.updateIsSend(favoriteAdPraiseWarnDTO.getId());

		 }
		 

	}

}
