package co.demo.service;

import co.demo.domain.AjaxRes;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;

public interface TransacationService {

    //余额查询功能
    AjaxRes searchMoney(String cardId);
    //取款功能
    AjaxRes drawMoney(String cardId, BigDecimal money, HttpSession session);
    //存款功能
    AjaxRes saveMoney(String cardId,BigDecimal money,HttpSession session);
    //转账功能
    AjaxRes transacMoney(String fromCard,String toCard,BigDecimal money,HttpSession session);
    //用来验证转账的账号是否存在
    //AjaxRes getUserCardInfo(String cardId,HttpSession session);
}
