package co.demo.service;

import co.demo.domain.AjaxRes;

public interface PasswordService {
    AjaxRes checkPassword(String cardId,String password);
    AjaxRes updatePassword(String cardId,String password);
}
