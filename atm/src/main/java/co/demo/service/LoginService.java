package co.demo.service;

import co.demo.domain.AjaxRes;
import javax.servlet.http.HttpSession;

public interface LoginService {
    AjaxRes getUserInfo(String cardId, HttpSession session);
    AjaxRes getCardInfo(String cardId,String password);

}
