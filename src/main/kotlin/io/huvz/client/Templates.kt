package io.huvz.client

import io.huvz.domain.vo.GiteeUsers
import io.quarkus.qute.CheckedTemplate
import io.quarkus.qute.TemplateInstance

@CheckedTemplate
object Templates {
    external fun gitee(users: GiteeUsers?): TemplateInstance?
}