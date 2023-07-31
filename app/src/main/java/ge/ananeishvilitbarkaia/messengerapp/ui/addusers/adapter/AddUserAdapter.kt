package ge.ananeishvilitbarkaia.messengerapp.ui.addusers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import ge.ananeishvilitbarkaia.messengerapp.databinding.ItemUserBinding
import ge.ananeishvilitbarkaia.messengerapp.model.User
import ge.ananeishvilitbarkaia.messengerapp.ui.addusers.`interface`.UserMessageItemListener
import ge.ananeishvilitbarkaia.messengerapp.utils.setImages
import java.util.ArrayList
import java.util.Locale

class AddUserAdapter(val context: Context, val users: ArrayList<User>, val listener: UserMessageItemListener): RecyclerView.Adapter<AddUserAdapter.ViewHolder>(),
    Filterable {
    private var mListFiltered: List<User> = users

    class ViewHolder(view: ItemUserBinding): RecyclerView.ViewHolder(view.root) {
        val binding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = users[position].name
        holder.binding.tvProfession.text = users[position].profession
        setImages(context, users[position].profileImage, holder.binding.ivProfile)

        holder.itemView.setOnClickListener {
            listener.onCLickUserItem(users[position])
        }

    }

    override fun getItemCount(): Int {
        return mListFiltered!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    mListFiltered = users
                } else {
                    val filteredList: MutableList<User> = ArrayList<User>()
                    for (row in users) {
                        if (row.name.toLowerCase()
                                .contains(charString.lowercase(Locale.getDefault())) || row.name
                                .toLowerCase().contains(
                                    charString.lowercase(
                                        Locale.getDefault()
                                    )
                                )
                        ) {
                            filteredList.add(row)
                        }
                    }
                    mListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mListFiltered = filterResults.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }
}