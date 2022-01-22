package com.example.myoptimizationbill.presenters.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myoptimizationbill.R
import com.example.domain.model.VoucherStatus
import java.text.DecimalFormat
import java.text.NumberFormat

class VoucherAdapter( val voucherClickCallback:VoucherClickCallback) : RecyclerView.Adapter<VoucherAdapter.ViewHolder>() {

     val mList: ArrayList<VoucherStatus> = ArrayList()
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_voucher, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        var percent_string=(100*itemsViewModel.percent_apply).toString()
        val formatter: NumberFormat = DecimalFormat("#,###")

        var apply_from_string=formatter.format( itemsViewModel.apply_from)
        var max_apply_string=formatter.format(itemsViewModel.maximum_quantity_apply)
        holder.it_voucher_discount.setText(percent_string)
        holder.it_voucher_apply_from.setText(apply_from_string)
        holder.it_voucher_max_apply.setText(max_apply_string)
        holder.it_voucher_edit.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                voucherClickCallback.clickEdit(itemsViewModel)
            }
        })

        holder.it_voucher_delete.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                voucherClickCallback.clickDelete(itemsViewModel)
            }
        })

        if (0 == position % 4)
            holder.it_voucher_holder_background .setBackgroundResource(R.color.plate_item0)
        if (1 == position % 4)
            holder.it_voucher_holder_background.setBackgroundResource(R.color.plate_item1)
        if (2 == position % 4)
            holder.it_voucher_holder_background.setBackgroundResource(R.color.plate_item2)
        if (3 == position % 4)
            holder.it_voucher_holder_background.setBackgroundResource(R.color.plate_item3)
//
//


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    fun updateListVoucher(newlist : ArrayList<VoucherStatus>)
    {

        mList.clear()
        mList.addAll(newlist)

        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(_itemView: View) : RecyclerView.ViewHolder(_itemView) {
        val it_voucher_discount = _itemView.findViewById<TextView>(R.id.it_voucher_discount)
        val it_voucher_apply_from = _itemView.findViewById<TextView>(R.id.it_voucher_apply_from)
        val it_voucher_max_apply = _itemView.findViewById<TextView>(R.id.it_voucher_max_apply)
        val it_voucher_edit = _itemView.findViewById<TextView>(R.id.it_voucher_edit)
        val it_voucher_delete = _itemView.findViewById<TextView>(R.id.it_voucher_delete)

        val it_voucher_holder_background = _itemView.findViewById<LinearLayout>(R.id.it_voucher_holder_background)



    }
    interface VoucherClickCallback {
        fun clickEdit(voucherStatus: com.example.domain.model.VoucherStatus)
        fun clickDelete(voucherStatus: com.example.domain.model.VoucherStatus)
    }
}
