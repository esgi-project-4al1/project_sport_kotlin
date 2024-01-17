package org.architecture.sport.server.mapper

import org.architecture.sport.domain.model.Enterprise
import org.architecture.sport.server.entity.EnterpriseEntity
import org.springframework.stereotype.Service

@Service
class EnterpriseEntityMapper {

    fun toServer(enterprise: Enterprise): EnterpriseEntity {
        return EnterpriseEntity(
            id = enterprise.id,
            name = enterprise.name,
            listUserOfEnterprise = enterprise.listUserOfEnterprise,
        )
    }


    fun toDomain(enterpriseEntity: EnterpriseEntity): Enterprise {
        return Enterprise(
            id = enterpriseEntity.id,
            name = enterpriseEntity.name,
            listUserOfEnterprise = enterpriseEntity.listUserOfEnterprise,
        )
    }
}