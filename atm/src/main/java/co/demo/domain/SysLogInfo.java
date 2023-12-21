package co.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "sysloginfo")
public class SysLogInfo {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer logId;   //日志编号
    private String cardId;   //银行卡号
    private String function;   //具体的操作
    private String params; //传入参数
    private Date optionalTime;  //操作时间

}
