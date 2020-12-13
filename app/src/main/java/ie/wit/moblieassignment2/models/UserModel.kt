package ie.wit.moblieassignment2.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var id: Int?, val username: String, val password: String): Parcelable