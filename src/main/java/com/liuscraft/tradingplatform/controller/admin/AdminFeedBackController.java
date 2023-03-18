package com.liuscraft.tradingplatform.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuscraft.tradingplatform.entity.Feedback;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.entity.vo.FeedbackVo;
import com.liuscraft.tradingplatform.entity.vo.UserVo;
import com.liuscraft.tradingplatform.service.IFeedbackService;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LiusCraft
 * @date 2023/3/18 17:53
 */
@RestController
@RequestMapping("admin/feedbacks")
public class AdminFeedBackController {
    @Resource
    IFeedbackService feedbackService;
    @Resource
    IUserService userService;
    @GetMapping
    public R getList(PageDto pageDto) {
        Page<Feedback> feedbackPage = new Page<>(pageDto.getPage(), pageDto.getLimit());
        IPage<Feedback> page = feedbackService.page(feedbackPage);
        Set<Integer> userIds = new HashSet<>();
        page.getRecords().forEach(v-> userIds.add(v.getUserId()));
        HashMap<Integer, UserVo> userVoHashMap = new HashMap<>();
        userService.userList(userIds).forEach(v-> userVoHashMap.put(v.getId(), v));
        return R.ok().data("data", page.getRecords().stream().map(v->{
            FeedbackVo feedbackVo = FeedbackVo.toVo(v);
            feedbackVo.setUserVo(userVoHashMap.get(v.getUserId()));
            return feedbackVo;
        }).collect(Collectors.toList())).data("count", page.getTotal());
    }
}
