package ie.wit.moblieassignment2

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class MyViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,false)
    }
}