package org.prcode.business.support.basic.security.config;

import org.apache.log4j.Logger;
import org.prcode.business.basedomain.role.domain.Role;
import org.prcode.business.support.basic.security.domain.CustomerUrlRoles;
import org.prcode.business.support.basic.security.service.ISecurityService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * @ClassName: CustomSecurityMetadataSource
 * @Date: 2017-4-16 20:20
 * @Auther: kangduo
 * @Description: (权限配置资源管理器)
 */
public class CustomerSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final Logger logger = Logger.getLogger(CustomerSecurityMetadataSource.class);

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private PathMatcher pathMatcher = new AntPathMatcher();

    private ISecurityService securityService;

    public CustomerSecurityMetadataSource(ISecurityService securityService) {
        super();
        this.securityService = securityService;
        resourceMap = loadResourceMatchAuthority();
    }

    private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthority() {
        List<CustomerUrlRoles> urlRoles = securityService.getUrlRoles();
        Map<String, Collection<ConfigAttribute>> map = new HashMap<>();
        if (urlRoles != null && !urlRoles.isEmpty()) {
            for (CustomerUrlRoles urlRole : urlRoles) {
                List<Role> roles = urlRole.getRoles();
                if (roles != null && roles.size() > 0) {
                    Collection<ConfigAttribute> list = new ArrayList<>(roles.size());
                    ConfigAttribute config;
                    for (Role role : roles) {
                        config = new SecurityConfig(role.getRoleCode());
                        list.add(config);
                    }
                    map.put(urlRole.getUrl(), list);
                }
            }
        } else {
            logger.error("'securityConfig.urlRoles' must be set");
        }
        logger.info("Loaded UrlRoles Resources.");
        return map;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String url = ((FilterInvocation) o).getRequestUrl();

        logger.debug("request url is  " + url);

        if (resourceMap == null) {
            resourceMap = loadResourceMatchAuthority();
        }

        for (String resURL : resourceMap.keySet()) {
            if (pathMatcher.match(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return resourceMap.get(url);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
