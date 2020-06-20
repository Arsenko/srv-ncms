package com.minnullin.repos

import com.minnullin.models.User
import com.minnullin.models.UserDto

interface UserRepository {
    suspend fun getAll(): List<User>
    suspend fun getById(id: Int): User?
    suspend fun getByUsername(username: String): User?
    suspend fun save(item: User): UserDto
}