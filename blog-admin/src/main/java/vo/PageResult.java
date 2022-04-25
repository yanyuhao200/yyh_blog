package vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: yyh
 * @Date: 2022/04/25 13:10
 */

@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;
}
