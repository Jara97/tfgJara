package com.example.tfgjara.data

import android.os.Parcel
import android.os.Parcelable

data class Champion(val name:String, var championId: String,var cost: Int,var traits: List<String>,var items:List<Int>) {


}

