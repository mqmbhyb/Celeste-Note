package com.bhyb.celestenote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.bhyb.celestenote.data.entity.LocalUserEntity

@Dao
interface LocalUserDao {
    @Insert
    suspend fun insertUser(user: LocalUserEntity)

    @Update
    suspend fun updateUser(user: LocalUserEntity)
}