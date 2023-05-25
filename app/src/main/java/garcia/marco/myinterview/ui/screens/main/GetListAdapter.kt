package garcia.marco.myinterview.ui.screens.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.databinding.ItemListBinding

class GetListAdapter(private val items: MutableList<GetListResponse>): RecyclerView.Adapter<GetListAdapter.ViewHolder>(), Filterable {

    var itemsOriginal: MutableList<GetListResponse>? = null

    init {
        itemsOriginal = items?.let { ArrayList(it) }
    }

    class ViewHolder(val binding: ItemListBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetListResponse) {
            with(binding) {
                tvName.text = item.nombre
                tvLastName.text = "${item.apellidoPaterno} ${item.apellidoMaterno}"
                tvBirthdate.text = item.fechaNac
                tvAge.text = "${item.edad} años"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun update(contentReservationBook: List<GetListResponse>) {
        items.clear()
        items.addAll(contentReservationBook)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charSearch = constraint.toString().toLowerCase()
            val resultList = if(charSearch.isNotEmpty()) {
                items.filter {
                    it.nombre.toLowerCase().contains(charSearch) ?: false
                }
            } else {
                itemsOriginal
            }
            Log.d("Busqueda", "resultList: ${resultList}")
            val filterResults = FilterResults()
            filterResults.values = resultList
            Log.d("Busqueda", "filterResults: ${filterResults}")
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            Log.d("Busqueda", "filterResults: ${filterResults?.values}")
            items.clear()
            items.addAll(filterResults?.values as MutableList<GetListResponse>)
            notifyDataSetChanged()

            if(items.isEmpty()) {
                Log.d("Busqueda", "Lista vacía")
                //onEmptySearch.invoke()
            }
        }

    }

}