package com.freedom.googlescholarshipproject.model

import android.os.Parcel
import android.os.Parcelable

class DataModel : Parcelable {
    private val id = 0
    var name: String? = null
    var hours: String? = null
    var country: String? = null
    var badgeUrl: String? = null

    constructor() {
//
    }

    constructor(name: String?, hours: String?, country: String?, badgeUrl: String?) {
        this.name = name
        this.hours = hours
        this.country = country
        this.badgeUrl = badgeUrl
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is DataModel) {
            return obj.name == name
        }
        return false
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeStringArray(arrayOf(
                name, hours, country, badgeUrl))
    }

    private constructor(`in`: Parcel) {
        val data = arrayOfNulls<String>(8)
        `in`.readStringArray(data)
        name = data[0]
        hours = data[1]
        country = data[2]
        badgeUrl = data[3]
    }

    companion object {
        val CREATOR: Parcelable.Creator<DataModel?> = object : Parcelable.Creator<DataModel?> {
            override fun createFromParcel(source: Parcel): DataModel? {
                return DataModel(source)
            }

            override fun newArray(size: Int): Array<DataModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}