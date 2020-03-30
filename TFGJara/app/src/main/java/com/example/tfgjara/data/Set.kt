package com.example.tfgjara.data

data class Set(var style: String,var min: Int,var efect:String) {

    override fun toString(): String {
        if(!efect.isEmpty()){
            return String.format("%s -> %s",min,efect)
        }
       else{
            return ""
        }
    }
}