package com.example.myoptimizationbill.presenters.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myoptimizationbill.R
import com.example.domain.model.BillStatus
import com.example.domain.model.OrderStatus
import com.example.domain.model.VoucherStatus

import com.example.myoptimizationbill.presenters.UI.DishSummary
import com.example.myoptimizationbill.presenters.UI.VoucherSummary
import com.example.myoptimizationbill.utils.Utils

class BillAdapter(val context: Context) :
    RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    var list_bills: ArrayList<BillStatus> = ArrayList()
    var list_vouchers: ArrayList<VoucherStatus> = ArrayList()
    var list_orders: ArrayList<OrderStatus> = ArrayList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bill_holder, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list_bills[position]
        var ids_voucher = item.ids_voucher
        var ids_order = item.ids_orders

        holder.tv_bill_name.setText("Bill " + position)

        (context as Activity).runOnUiThread(Runnable {

            for (id_voucher in ids_voucher) {
                var customViewVoucher: View? = null

                var voucher_info: VoucherStatus? = null
                for (my_voucher_info in list_vouchers)
                    if (my_voucher_info.id.equals(id_voucher)) {
                        voucher_info = my_voucher_info
                        break
                    }

                customViewVoucher =
                    getViewOfVoucer(voucher_info, object : VoucherSummary.OnGenerateView {
                        var isUpdated = false
                        override fun done(w: Int, h: Int) {
//                        synchronized(isUpdated)
//                        {
//                            if (false == isUpdated) {
//
//                            }
//                        }
                        }
                    })

                holder.ll_vouchers.addView(customViewVoucher)
            }

            var total_amount_order = 0

            for (id_order in ids_order) {
                var customViewOrder: View? = null
                var orderInfoStatus: OrderStatus? = null

                for (order in list_orders) {

                    if (order.id.equals(id_order)) {
                        orderInfoStatus = order
                        total_amount_order += order.price
                        break
                    }
                }
                customViewOrder = getViewOfOrder(orderInfoStatus)

                holder.ll_sumary_dishes.addView(customViewOrder)
            }

            var total_bill=Utils.formatCurrency(total_amount_order.toInt().toString())
            holder.tv_bill_total.text = "(${total_bill})"

        })


    }

    fun updateListBill(
        new_bill_list: ArrayList<BillStatus>,
        new_voucher_list: ArrayList<VoucherStatus>,
        new_order_list: ArrayList<OrderStatus>
    ) {
        list_bills.clear()
        list_bills.addAll(new_bill_list)


        list_orders.clear()
        list_orders.addAll(new_order_list)

        list_vouchers.clear()
        list_vouchers.addAll(new_voucher_list)

        notifyDataSetChanged()
    }


    fun getViewOfOrder(orderStatus: OrderStatus?): View {
        val view = DishSummary(context).getView()


        var item_dish_eating: TextView = view.findViewById(R.id.item_dish_eating)
        var item_owner_name: TextView = view.findViewById(R.id.item_owner_name)
        var item_dish_price: TextView = view.findViewById(R.id.item_dish_price)

        item_dish_eating.setText(orderStatus?.dish_name)
        item_owner_name.setText(orderStatus?.owner_name)
        var order_price:String=Utils.formatCurrency(orderStatus?.price!!.toInt().toString())
        item_dish_price.setText(order_price)

        return view
    }

    fun getViewOfVoucer(
        voucherStatus: VoucherStatus?,
        callback: VoucherSummary.OnGenerateView
    ): View {
        val view = VoucherSummary(context, callback).getView()
        var it_voucher_max_apply: TextView = view.findViewById(R.id.it_voucher_max_apply)
        var it_voucher_discount: TextView = view.findViewById(R.id.it_voucher_discount)

        var maxAppy= Utils.formatCurrency((voucherStatus?.maximum_quantity_apply!!.toInt()).toString())

        it_voucher_max_apply.setText(maxAppy)

        it_voucher_discount.setText((100 * voucherStatus!!.percent_apply).toString())
        return view
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list_bills.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(_itemView: View) : RecyclerView.ViewHolder(_itemView) {

        var tv_bill_name = _itemView.findViewById<TextView>(R.id.tv_bill_name)
        var tv_bill_total = _itemView.findViewById<TextView>(R.id.tv_bill_total)
        var ll_vouchers = _itemView.findViewById<LinearLayout>(R.id.ll_vouchers)
        var ll_sumary_dishes = _itemView.findViewById<LinearLayout>(R.id.ll_sumary_dishes)

    }

    interface DishCallback {
        fun clickEdit(dishStatus: OrderStatus)
        fun clickDelete(dishStatus: OrderStatus)
    }
}
