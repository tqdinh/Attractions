package com.example.attractions

sealed class LanguageDefine(val code: String, val displayName: String) {
    object English : LanguageDefine("en", "English")
    object Vietnamese : LanguageDefine("vi", "Vietnamese")
    object Spanish : LanguageDefine("es", "Spanish")
    object ChineseSimplified : LanguageDefine("zh-cn", "Chinese (Simplified)")
    object ChineseTraditional : LanguageDefine("zh-tw", "Chinese (Traditional)")
    object Japanese : LanguageDefine("ja", "Japanese")
    object Korean : LanguageDefine("ko", "Korean")
    object Indonesian : LanguageDefine("id", "Indonesian")
    object Thai : LanguageDefine("th", "Thai")
}