package com.kotlin.mywallet.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge

data class AccountWithCharges(

    @Embedded val account: Account,
    @Relation(
        parentColumn = "accountName",
        entityColumn = "accountName"
    )

    val charges: List<Charge>

    )
