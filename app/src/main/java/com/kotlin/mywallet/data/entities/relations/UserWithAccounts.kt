package com.kotlin.mywallet.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.User

data class UserWithAccounts(

    @Embedded val user: User,
    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )

    val accounts: List<Account>

)
