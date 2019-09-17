package cn.gw.demo2.qq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Birthday {
    private String year;
    private String month;
    private String day;
}
