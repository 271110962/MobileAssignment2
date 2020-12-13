package ie.wit.moblieassignment2.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemoModel(var id: Int?, var title: String, var category: String, var description: String, var username: String): Parcelable