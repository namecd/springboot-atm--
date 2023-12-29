package co.demo.service.impl;

import co.demo.common.enums.ExceptionEnum;
import co.demo.common.exception.AtmException;
import co.demo.domain.AjaxRes;
import co.demo.domain.CardInfo;
import co.demo.mapper.LoginMapper;
import co.demo.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private LoginMapper loginMapper;

    /**
     * 根据账户密码判断用户信息是否正确
     *
     * @param cardId
     * @param password
     * @return
     */
    @Override
    public AjaxRes checkPassword(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        cardInfo.setPassword(password);
        try {
            CardInfo card = loginMapper.selectOne(cardInfo);
            if (StringUtils.isEmpty(card)) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("密码错误");
            } else {
                ajaxRes.setRes("success");
                ajaxRes.setMeg("成功");
            }
        } catch (Exception e) {
            log.error("密码修改失败");
            throw new AtmException(ExceptionEnum.LOGIN_ERROE);
        }
        return ajaxRes;
    }

    /**
     * 根据卡号修改密码
     *
     * @param cardId
     * @param password
     * @return
     */
    @Override
    public AjaxRes updatePassword(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try {
            CardInfo card = loginMapper.selectOne(cardInfo);
            card.setPassword(password);
            int i = loginMapper.updateByPrimaryKey(card);
            if (i > 0) {
                ajaxRes.setRes("success");
                ajaxRes.setMeg("密码修改成功");
            } else {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("修改密码失败");
            }
        } catch (Exception e) {
            log.error("密码修改失败");
            throw new AtmException(ExceptionEnum.UPDATE_PASSWORD_ERROR);
        }
        return ajaxRes;
    }

}
