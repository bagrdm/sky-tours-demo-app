package com.dm.sky_tours_demo_app.ui.fragments.utils

import android.text.Editable
import android.text.TextWatcher

class AppTextWatcher(val onSuccess: (Editable?) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        onSuccess
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onSuccess
    }
    override fun afterTextChanged(s: Editable?) {
        onSuccess(s)
    }
}