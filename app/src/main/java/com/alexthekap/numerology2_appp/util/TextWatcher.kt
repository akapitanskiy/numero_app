package com.alexthekap.numerology2_appp.util

import android.text.Editable
import android.text.TextWatcher
import androidx.core.util.Consumer

open class TxtChangeListener(
    private val listener: Consumer<String>
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) { /* DO NOTHING */ }
    override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) { /* DO NOTHING */ }

    override
    fun afterTextChanged(s: Editable?) {
        listener.accept(s.toString())
    }
}

