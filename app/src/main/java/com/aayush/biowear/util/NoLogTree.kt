package com.aayush.biowear.util

import timber.log.Timber.Tree

class NoLogTree: Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {}
}