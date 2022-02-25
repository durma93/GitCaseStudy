package io.project.gitcasestudy.network

import io.project.gitcasestudy.model.GitResponse
import io.project.gitcasestudy.model.Owner
import io.project.gitcasestudy.model.pojo.GitObject
import io.project.gitcasestudy.utils.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<GitResponse, GitObject> {
    override fun mapFromEntity(entity: GitResponse): GitObject {
        return GitObject(
            id = entity.id,
            title = entity.name,
            language = entity.language,
            numberOfWatchers = entity.watchers,
            description = entity.description,
            loginName = entity.owner.login,
            repositoryUpdateDate = entity.updatedAt
        )
    }

    override fun mapToEntity(domainModel: GitObject): GitResponse {
        return GitResponse(
            id = domainModel.id,
            name = domainModel.title!!,
            language = domainModel.language!!,
            watchers = domainModel.numberOfWatchers!!,
            description = domainModel.description!!,
            owner = Owner(domainModel.loginName!!),
            updatedAt = domainModel.repositoryUpdateDate!!
        )
    }

    fun mapFromEntityList(entities: List<GitResponse>): List<GitObject> {
        return entities.map { mapFromEntity(it) }
    }
}