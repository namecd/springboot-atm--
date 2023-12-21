package co.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Table(name = "cardinfo")
public class CardInfo {
    @Id
    private String cardId; //银行卡的编号
    private Boolean savingType; //0活期，1定期
    private BigDecimal store;   //余额，完全准确的浮点数类型，避免误差。
    private BigDecimal prestore;   //预存余额
    private String password;    //密码
    private Integer status;  //银行卡状态，挂失，冻结，正常等，挂失即挂失并冻结
    private Integer userId; //绑定账号
}
