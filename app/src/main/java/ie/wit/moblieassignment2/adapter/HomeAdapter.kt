package ie.wit.moblieassignment2.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.listener.MemoListener
import ie.wit.moblieassignment2.models.MemoModel
import kotlinx.android.synthetic.main.card_memo.view.*
import org.jetbrains.anko.colorAttr
import java.io.IOException


class HomeAdapter constructor(private var memos: ArrayList<MemoModel>,
                              private val listener: MemoListener,
                              private val context: Context) : RecyclerView.Adapter<HomeAdapter.MainHolder>(),Filterable {

    var mFilterList:ArrayList<MemoModel> = memos


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_memo, parent, false))
    }

    override fun getItemCount(): Int {
        return mFilterList.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val memo = mFilterList[holder.adapterPosition]
        holder.bind(memo, listener,context)
    }
    //implemented by the filterable interface. It can bind the search box and filtering the list by memo title.
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                //Nothing in the search box will return completed list
                val charString = p0.toString()
                if(charString.isEmpty()){
                    mFilterList = memos
                }else{
                    //otherwise return the list which contains the memo title
                    var filteredList = ArrayList<MemoModel>()
                    for(i in memos){
                        if(i.title.contains(charString)){
                            filteredList.add(i)
                        }
                    }
                    mFilterList = filteredList
                }
                var filterResults = FilterResults()
                filterResults.values = mFilterList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mFilterList = p1?.values as ArrayList<MemoModel>
                notifyDataSetChanged()
            }

        }
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(memo: MemoModel, listener: MemoListener,context: Context) {
            itemView.cardTitle.text = memo.title
            itemView.cardDescription.text = memo.description
            if(memo.category == "Study") {
                itemView.card_layout.background.setTint(Color.YELLOW)
                itemView.card_icon.setImageBitmap(readImage(R.mipmap.book, context))
            }
            else if(memo.category == "Life"){
                itemView.card_layout.background.setTint(Color.GREEN)
                itemView.card_icon.setImageBitmap(readImage(R.mipmap.me_nonclicked, context))
            }
            else{
                itemView.card_layout.background.setTint(Color.CYAN)
                itemView.card_icon.setImageBitmap(readImage(R.mipmap.idea, context))
            }
            itemView.setOnClickListener{listener.onMemoClick(memo)}
        }

        fun readImage(image:Int,context: Context):Bitmap?{
            var bitmap: Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeResource(context.resources, image)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return bitmap

        }
    }


}