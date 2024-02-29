package io.huvz.client

import io.huvz.domain.vo.GiteeUsers
import jakarta.enterprise.context.ApplicationScoped

/**
 * 缓存上次搜索的结果
 */
@ApplicationScoped
class LocalCache {
    var cache: MutableList<GiteeUsers> = ArrayList();
}