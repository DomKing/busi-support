package org.prcode.business.support.basic.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BusiSupportBasicCacheDao
 * @Date: 2017-03-31 17:24
 * @Auther: kangduo
 * @Description: (这是demo, 正确情况应该是按业务来分包)
 */
@Repository
public interface BusiSupportBasicCacheDao {

    String getSysParamValue(String param);

    /**
     * 更新参数表
     * Param 注解会把参数组成一个map
     * @param param param
     * @param value value
     */
    void updSysParamValue(@Param("param") String param, @Param("value") String value);
}
