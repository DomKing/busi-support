package org.prcode.business.support.basic.busiSupportCache.service;

/**
 * @ClassName: IBusiSupportCacheService
 * @Date: 2017-04-01 14:52
 * @Auther: kangduo
 * @Description: (param缓存)
 */
public interface IBusiSupportCacheService {
    String getCommSysParaValue(String paraCode);

    String updCommSysParaValue(String paraCode, String paraValue);

    void delCommSysParaValue(String paraCode);

    String getOmsCommSysParaValue(String paraCode);

    String updOmsCommSysParaValue(String paraCode, String paraValue);

    void delOmsCommSysParaValue(String paraCode);

    void refreshSysParaCache();
}
