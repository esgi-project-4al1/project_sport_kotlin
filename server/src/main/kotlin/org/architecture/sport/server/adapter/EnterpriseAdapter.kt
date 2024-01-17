package org.architecture.sport.server.adapter

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Enterprise
import org.architecture.sport.domain.ports.server.EnterprisePersistenceSpi
import org.architecture.sport.server.mapper.EnterpriseEntityMapper
import org.architecture.sport.server.repository.EnterpriseRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class EnterpriseAdapter(
    private val enterpriseRepository: EnterpriseRepository,
    private val enterpriseMapper: EnterpriseEntityMapper,
) : EnterprisePersistenceSpi {
    override fun addUsers(id: UUID, userId: UUID): Either<ApplicationError, Enterprise> {
        try {
            val enterprise = enterpriseRepository.findById(id).get()
            val listUserOfEnterprise = enterprise.listUserOfEnterprise + userId
            val enterpriseSave = enterpriseRepository.save(enterprise.copy(listUserOfEnterprise = listUserOfEnterprise))
            return enterpriseSave.let { enterpriseMapper.toDomain(it) }.right()
        } catch (e: Exception) {
            return ApplicationError(
                message = "Error when adding user to enterprise",
                context = "EnterpriseAdapter.addUsers",
            ).left()
        }
    }

    override fun removeUsers(enterpriseId: UUID, userId: UUID): Either<ApplicationError, Enterprise> {
        try {
            val enterprise = enterpriseRepository.findById(enterpriseId).get()
            val listUserOfEnterprise = enterprise.listUserOfEnterprise - userId
            val enterpriseSave = enterpriseRepository.save(enterprise.copy(listUserOfEnterprise = listUserOfEnterprise))
            return enterpriseSave.let { enterpriseMapper.toDomain(it) }.right()
        } catch (e: Exception) {
            return ApplicationError(
                message = "Error when removing user to enterprise",
                context = "EnterpriseAdapter.removeUsers",
            ).left()
        }
    }

    override fun isUserAlreadyInEnterprise(userId: UUID): Boolean {
        return enterpriseRepository.findByListUserOfEnterpriseContaining(userId).isNotEmpty()
    }

    override fun isUserIsInEnterprise(enterpriseId: UUID, userId: UUID): Boolean {
        return enterpriseRepository.findById(enterpriseId).get().listUserOfEnterprise.contains(userId)
    }

    override fun get(id: UUID): Enterprise? {
        return enterpriseRepository.findById(id).map { enterpriseMapper.toDomain(it) }.orElse(null)
    }

    override fun getAll(): List<Enterprise> {
        return enterpriseRepository.findAll().map { enterpriseMapper.toDomain(it) }
    }

    override fun getEnterpriseByUserId(userId: UUID): Enterprise? {
        return enterpriseRepository.findByListUserOfEnterpriseContaining(userId).firstOrNull()?.let {
            enterpriseMapper.toDomain(it)
        }
    }

    override fun save(o: Enterprise): Either<ApplicationError, Enterprise> {
        try {
            val enterprise = enterpriseRepository.save(enterpriseMapper.toServer(o))
            return enterpriseMapper.toDomain(enterprise).right()
        } catch (e: Exception) {
            return ApplicationError(
                message = "Error when saving enterprise",
                context = "EnterpriseAdapter.save",
            ).left()
        }
    }

    override fun findById(id: UUID): Enterprise? {
        return enterpriseRepository.findById(id).map { enterpriseMapper.toDomain(it) }.orElse(null)
    }

}