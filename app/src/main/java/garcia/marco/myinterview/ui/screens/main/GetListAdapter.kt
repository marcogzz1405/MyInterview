package garcia.marco.myinterview.ui.screens.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.databinding.ItemListBinding

class GetListAdapter(private val items: MutableList<GetListResponse>): RecyclerView.Adapter<GetListAdapter.ViewHolder>() {

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

}