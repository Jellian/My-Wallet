package com.kotlin.mywallet.utils

import android.accounts.Account
import com.google.android.gms.common.util.Strings
import com.kotlin.mywallet.finance.Cuenta


internal fun getNumberAccounts(accounts: List<Cuenta>?):Int{

    return accounts?.size ?: 0

}

//internal fun verifyExpenseorIncome(){
//}