package com.liuscraft.tradingplatform.controller;


import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.entity.Feedback;
import com.liuscraft.tradingplatform.service.IFeedbackService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-18
 */
@RestController
@RequestMapping("feedback")
@LuckVerify
public class FeedbackController {
    @Resource
    IFeedbackService feedbackService;
    @PostMapping
    public R addFeedBack(@RequestBody Feedback feedback) {
        feedback.setId(null);
        feedback.setDeleted(false);
        Integer userId = ThreadLocalServlet.getUserId();
        feedback.setUserId(userId);
        if(feedbackService.save(feedback))return R.ok().msg("反馈成功");
        return R.error().msg("反馈失败");
    }
}

