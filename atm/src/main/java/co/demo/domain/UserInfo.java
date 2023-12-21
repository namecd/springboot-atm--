package co.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "userinfo")
public class UserInfo {
    @KeySql(useGeneratedKeys = true)
    /*
    *数据库中，用户对银行卡是一对多关系，所以在这里不用
    *指明绑定的银行卡信息，而是在银行卡信息指定userId;
    */
    private Integer userId; //用户在银行中的编号
    private String userName; //用户的名字
    private String personId; //用户的身份证号码
    private String telephone; //用户的电话号码

}
