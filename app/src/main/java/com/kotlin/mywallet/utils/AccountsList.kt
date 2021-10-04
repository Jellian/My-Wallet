package com.kotlin.mywallet.utils

import android.accounts.Account
import com.kotlin.mywallet.finance.Cuenta


internal fun getNumberAccounts(accounts: List<Cuenta>?):Int{

    return accounts?.size ?: 0

}