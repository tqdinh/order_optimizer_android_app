package com.example.myoptimizationbill.presenters.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.OrderStatus
import com.example.myoptimizationbill.R

import java.text.DecimalFormat
import java.text.NumberFormat

class OrderAdapter(val dishCallback: DishCallback) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var order_list: ArrayList<OrderStatus> = ArrayList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = order_list[position]
        if (0 == position % 4)
            holder.item_view_parent.setBackgroundResource(R.color.plate_item0)
        if (1 == position % 4)
            holder.item_view_parent.setBackgroundResource(R.color.plate_item1)
        if (2 == position % 4)
            holder.item_view_parent.setBackgroundResource(R.color.plate_item2)
        if (3 == position % 4)
            holder.item_view_parent.setBackgroundResource(R.color.plate_item3)

        (holder.item_owner_name as TextView).setText(item.owner_name)
        (holder.item_dish_eating as TextView).setText(item.dish_name)

        val formatter: NumberFormat = DecimalFormat("#,###")

        val formattedNumber: String = formatter.format(item.price)

        (holder.item_dish_price as TextView).setText(formattedNumber)
        holder.item_dish_edit.setOnClickListener(View.OnClickListener {
            dishCallback.clickEdit(item)
        })

        holder.item_dish_delete.setOnClickListener(View.OnClickListener {
            dishCallback.clickDelete(item)
        })


        // sets the text to the textview from our itemHolder class
        //holder.textView.text = ItemsViewModel.name

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return order_list.size
    }


    fun updateListVoucher(newlist: ArrayList<OrderStatus>) {
        order_list.clear()

        order_list.addAll(newlist)

        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(_itemView: View) : RecyclerView.ViewHolder(_itemView) {
        val item_owner_name = _itemView.findViewById<View>(R.id.item_owner_name)
        val item_dish_eating = _itemView.findViewById<View>(R.id.item_dish_eating)
        val item_dish_price = _itemView.findViewById<View>(R.id.item_dish_price)
        val item_dish_edit = _itemView.findViewById<View>(R.id.item_dish_edit)
        val item_dish_delete = _itemView.findViewById<View>(R.id.item_dish_delete)
        val item_view_parent = _itemView.findViewById<ConstraintLayout>(R.id.item_view_parent)


    }

    interface DishCallback {
        fun clickEdit(dishStatus: OrderStatus)
        fun clickDelete(dishStatus: OrderStatus)
    }
}
