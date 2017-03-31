package org.prcode.business.support.basic.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BusiSupportBasicCacheDao
 * @Date: 2017-03-31 17:24
 * @Auther: kangduo
 * @Description: ()
 */
@Repository
public interface BusiSupportBasicCacheDao {

    @Cacheable(value = "mysql:sysParam", key = "#p0")
    String getSysParamValue(String param);
}
