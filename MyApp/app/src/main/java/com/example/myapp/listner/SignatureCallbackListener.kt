package com.example.myapp.listner

import java.io.File

interface SignatureCallbackListener {
    fun signatureCallback(file: File)
}