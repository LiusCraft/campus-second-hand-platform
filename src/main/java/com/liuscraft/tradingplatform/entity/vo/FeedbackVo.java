package com.liuscraft.tradingplatform.entity.vo;

import com.liuscraft.tradingplatform.entity.Feedback;
import jdk.nashorn.internal.objects.annotations.Function;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author LiusCraft
 * @date 2023/3/18 21:14
 */
@Data
public class FeedbackVo {
    private String context;
    private Integer id;
    private UserVo userVo;
    @Function
    public static FeedbackVo toVo(Feedback feedback) {
        FeedbackVo feedbackVo = new FeedbackVo();
        BeanUtils.copyProperties(feedback, feedbackVo);
        return feedbackVo;
    }
}
