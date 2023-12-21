package co.demo.service.impl;

import co.demo.common.exception.ExceptionsForAtm;
import co.demo.mapper.CardInfoMapper;
import co.demo.mapper.UserInfoMapper;
import co.demo.common.enums.ExceptionEnum;
import co.demo.domain.AjaxRes;
import co.demo.domain.CardInfo;
import co.demo.domain.UserInfo;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.demo.service.LoginService;


@Service
@Slf4j  //简单的日志门面
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private CardInfoMapper cardInfoMapper;

    @Override
    public AjaxRes getCardInfo(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo =new CardInfo();
        cardInfo.setCardId(cardId);
        cardInfo.setPassword(password);
        try{
            CardInfo card = cardInfoMapper.selectOne(cardInfo);
            if(StringUtils.isEmpty(card)){
                //StringUtils.isEmpty()用来检测字符串是否为空或 null，
                //并不仅限于字符串类型，它也可以接受任意对象作为参数。
                //在这种情况下，检查card对象是否为 null。
                ajaxRes.setRes("error");
                ajaxRes.setMesg("密码错误！");
            }
            else{
                UserInfo userInfo=userInfoMapper.selectByPrimaryKey(card.getUserId());
                ajaxRes.setRes("success");
                ajaxRes.setMesg("登陆成功！");
                ajaxRes.setObject(userInfo);
            }
        }
        catch (Exception a){
            log.error("登录失败");
            throw new ExceptionsForAtm(ExceptionEnum.LOGIN_ERROE);
        }
        return ajaxRes;
    }
    //读卡登录模块
    @Override
    public AjaxRes getUserInfo(String cardId, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        try{
            //传入的参数应该是主键的一个值，
            //并且数据类型应该与主键字段的类型相匹配。
            CardInfo cardInfo = cardInfoMapper.selectByPrimaryKey(cardId);
            if (StringUtils.isEmpty(cardInfo)){
                ajaxRes.setRes("error");
                ajaxRes.setMesg("卡号不存在");
            }else{
                ajaxRes.setRes("success");
                ajaxRes.setMesg(cardId);
                session.setAttribute("cardId",cardId);
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new ExceptionsForAtm(ExceptionEnum.READ_CARD_ERROE);
        }
        return ajaxRes;
    }
}
