package com.dm.sky_tours_demo_app.ui.fragments.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

fun Fragment.switchFragment(fm: FragmentManager, @IdRes container: Int, fragment: Fragment) {
    fm.commit {
        replace(container, fragment)
    }
}
