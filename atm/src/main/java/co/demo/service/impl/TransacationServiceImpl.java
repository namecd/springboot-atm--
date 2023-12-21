package co.demo.service.impl;

import co.demo.common.exception.ExceptionsForAtm;
import co.demo.mapper.CardInfoMapper;
import co.demo.mapper.UserInfoMapper;
import co.demo.mapper.TransInfoMapper;
import co.demo.common.enums.ExceptionEnum;
import co.demo.domain.AjaxRes;
import co.demo.domain.CardInfo;
import co.demo.domain.TransInfo;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import co.demo.service.TransacationService;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@Transactional
public class TransacationServiceImpl implements TransacationService {
    @Autowired
    private CardInfoMapper cardInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TransInfoMapper transInfoMapper;

    //用户查询余额功能
    @Override
    public AjaxRes searchMoney(String cardId) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try{
            CardInfo card = cardInfoMapper.selectOne(cardInfo);
            if(StringUtils.isEmpty(card)){
                ajaxRes.setRes("error");
                ajaxRes.setMesg("未查询到结果");
            }
            else{
                ajaxRes.setRes("success");
                ajaxRes.setMesg("查询成功");
                ajaxRes.setObject(card);
            }
        }catch (Exception e){
            log.error("查询金额失败" + e.getMessage());
            throw new ExceptionsForAtm(ExceptionEnum.QUERY_MONEY_ERROE);
        }
        return ajaxRes;
    }

    //存款功能
    @Override
    public AjaxRes saveMoney(String cardId, BigDecimal money, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try {
            //根据用户id查询用户信息
            CardInfo cardInfo1 = cardInfoMapper.selectOne(cardInfo);
            cardInfo1.setStore(cardInfo1.getStore().add(money));
            cardInfoMapper.updateByPrimaryKey(cardInfo1);

            //保存交易记录
            TransInfo trans = new TransInfo();
            trans.setCardId(cardInfo1.getCardId());
            trans.setTransType(0);
            trans.setTransMoney(money);
            trans.setTransDate(new Date());
            transInfoMapper.insert(trans);


            //保存到session中
            session.setAttribute("transInfo", trans);
            ajaxRes.setRes("success");
            ajaxRes.setMesg(money + "");
        } catch (Exception e) {
            log.error("存款失败" + e.getMessage());
            throw new ExceptionsForAtm(ExceptionEnum.SAVE_MONEY_ERROR);
        }
        return ajaxRes;
    }

    //取款功能
    @Override
    public AjaxRes drawMoney(String cardId, BigDecimal money, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try {
            //根据用户id查询用户信息
            CardInfo cardInfo1 = cardInfoMapper.selectOne(cardInfo);
            //账户余额
            BigDecimal store = cardInfo1.getStore();
            //账户预存余额
            BigDecimal prestore = cardInfo1.getPrestore();
            //账户可用余款
            BigDecimal subtract = store.subtract(prestore);
            //比较，-1小于、0等于、1大于
            int i = money.compareTo(subtract);
            if (i < 0) {
                BigDecimal subtract1 = store.subtract(money);
                cardInfo1.setStore(subtract1);
                cardInfoMapper.updateByPrimaryKey(cardInfo1);

                //保存交易记录
                TransInfo transInfo = new TransInfo();
                transInfo.setCardId(cardInfo1.getCardId());
                transInfo.setTransType(1);
                transInfo.setTransMoney(money);
                transInfo.setTransDate(new Date());
                transInfoMapper.insert(transInfo);


                //保存到session中
                session.setAttribute("transInfo", transInfo);
                ajaxRes.setRes("success");
                ajaxRes.setMesg(money + "");
            } else if (i > 0) {
                ajaxRes.setRes("error");
                ajaxRes.setMesg("账户余额不足");
            }
        } catch (Exception e) {
            log.error("取款失败" + e.getMessage());
            throw new ExceptionsForAtm(ExceptionEnum.WITHDRAWAL_ERROE);
        }
        return ajaxRes;
    }

    //转账功能
    @Override
    public AjaxRes transacMoney(String fromCard, String toCard, BigDecimal money, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            //判断当前账户的可用余额是否足够完成本次交易
            CardInfo cardInfo = cardInfoMapper.selectByPrimaryKey(fromCard);
            BigDecimal store = cardInfo.getStore();
            BigDecimal subtract = store.subtract(cardInfo.getPrestore());
            //比较，-1小于、0等于、1大于
            int i = subtract.compareTo(money);
            if (i >= 0) {
                //用户余额减去金额
                cardInfo.setStore(cardInfo.getStore().subtract(money));
                cardInfoMapper.updateByPrimaryKey(cardInfo);
                //对方金额增加
                CardInfo cardInfo2 = cardInfoMapper.selectByPrimaryKey(toCard);
                cardInfo2.setStore(cardInfo2.getStore().add(money));
                cardInfoMapper.updateByPrimaryKey(cardInfo2);

                //保存交易记录
                TransInfo trans = new TransInfo();
                trans.setCardId(fromCard);
                trans.setTransType(2);
                trans.setTransMoney(money);
                trans.setTransDate(new Date());
                trans.setRemarks(toCard);
                transInfoMapper.insert(trans);


                ajaxRes.setRes("success");
                ajaxRes.setMesg("交易成功");
                //保存到session中
                session.setAttribute("transInfo", trans);
            } else {
                ajaxRes.setRes("error");
                ajaxRes.setMesg("账户余额不足,无法完成交易！");
            }
        }catch (Exception e){
            log.error("转账异常"+e.getMessage());
            throw new ExceptionsForAtm(ExceptionEnum.TRANSFER_ACCOUNT_ERROE);
        }
        return ajaxRes;
    }

    //验证转账的账户是否存在，作为api被前端调用
    /*@Override
    public AjaxRes getUserCardInfo(String cardId, HttpSession session) {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        AjaxRes ajaxRes = new AjaxRes();
        try {
            //根据卡号查询用户信息
            CardInfo cardInfo1 = cardInfoMapper.selectOne(cardInfo);
            if (StringUtils.isEmpty(cardInfo1)) {
                ajaxRes.setRes("error");
                ajaxRes.setMesg("输入卡号不存在，请检查后重试");
            } else {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(cardInfo1.getUserId());
                ajaxRes.setRes("success");
                ajaxRes.setMesg(cardId);
                ajaxRes.setObject(userInfo);
            }
        } catch (Exception e) {
            log.error("转账异常" + e.getMessage());
            throw new ExceptionsForAtm(ExceptionEnum.TRANSFER_ACCOUNT_FOUND);
        }
        return ajaxRes;
    }*/
}
