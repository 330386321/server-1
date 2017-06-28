package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.JobsConfig;
import com.lawu.eshop.jobs.service.RongYunDownLoadService;
import com.lawu.eshop.jobs.service.RongYunService;
import com.lawu.eshop.user.dto.RongYunHistoryMessageDTO;
import com.lawu.eshop.utils.DateUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.DownLoadUtil;

/**
 * @author zhangyong
 * @date 2017/5/22.
 */
@Service
public class RongYunDownLoadServiceImpl implements RongYunDownLoadService {
    private static Logger logger = LoggerFactory.getLogger(RongYunDownLoadServiceImpl.class);
    @Autowired
    private RongYunService rongYunService;
    @Autowired
    private JobsConfig jobsConfig;

    @Override
    public void downLoadMessageFile() {
        Date date = DateUtil.getDayBefore(new Date());//获取前一天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateFormat = sdf.format(date);
        String dateStr;
        for (int i = 1; i < 24; i++) {
            if (i < 10) {
                dateStr = dateFormat + "0" + i;
            } else {
                dateStr = dateFormat + i;
            }
            //获取融云聊天记录
            Result<RongYunHistoryMessageDTO> messageDTOResult = rongYunService.getHistoryMessage(dateStr);
            RongYunHistoryMessageDTO messageDTO = messageDTOResult.getModel();
            if (StringUtils.isNotEmpty(messageDTO.getUrl())) {
                try {
                    DownLoadUtil.downLoadFromUrl(messageDTO.getUrl(), dateStr+".zip", jobsConfig.getDownLoadDir() + File.separator+ dateFormat);
                } catch (IOException e) {
                    logger.error("下载消息失败！IO异常",e);
                }
            }
        }
    }
}
