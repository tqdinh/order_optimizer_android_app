package com.example.myoptimizationbill.presenters.Fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myoptimizationbill.R
import com.example.domain.model.*
import com.example.myoptimizationbill.framework.MyViewModelFactory
import com.example.myoptimizationbill.presenters.Adapter.OrderAdapter
import com.example.myoptimizationbill.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import id.ionbit.ionalert.IonAlert
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OrdersFragment : BaseFragment(), OrderAdapter.DishCallback {


    private lateinit var bt_add_dish: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter

    lateinit var viewModel: OrderViewModel

    private lateinit var et_shipping: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_dishes, container, false)

        viewModel = ViewModelProviders.of(this, MyViewModelFactory).get(OrderViewModel::class.java)

        et_shipping=view.findViewById(R.id.et_shipping)

        viewModel.orderLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateListVoucher(it)

        })

        recyclerView = view.findViewById(R.id.rv_dishes)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)

        viewModel.test()


        bt_add_dish = view.findViewById(R.id.bt_add_dish)
        bt_add_dish.setOnClickListener(View.OnClickListener {
            showDialogEdit(null)
        })

        et_shipping.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                et_shipping.removeTextChangedListener(this);


                var formatted:String= Utils.formatCurrency(s.toString())
                et_shipping.setText(formatted);
                et_shipping.setSelection(formatted.length);
                et_shipping.addTextChangedListener(this);

                Handler().postDelayed(Runnable {
                    if(s?.length != 0) {


                        var vDouble: Double = s.toString().toDouble()
                        viewModel.setShippingFee(vDouble)
                    }
                },1000)
            }

            override fun afterTextChanged(s: Editable?) {


            }
        })

        et_shipping.setText((22000).toString())
        adapter = OrderAdapter(this)
        recyclerView.adapter = adapter

        return view
    }

    fun showDialogEdit(orderStatus: OrderStatus?) {
        val builder = AlertDialog.Builder(requireContext())
        // Get the layout inflater
        val inflater = this.layoutInflater;

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val customview = inflater.inflate(R.layout.dialog_item_dish, null)
        var et_item_dish_eating = customview.findViewById<EditText>(R.id.et_item_dish_eating)
        var et_item_owner_name = customview.findViewById<EditText>(R.id.et_item_owner_name)
        var et_item_dish_price = customview.findViewById<EditText>(R.id.et_item_dish_price)

        et_item_dish_price.addTextChangedListener(object : TextWatcher {

            private var current = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().equals(current)) {
                    et_item_dish_price.removeTextChangedListener(this);


                    var formatted:String= Utils.formatCurrency(s.toString())
                    et_item_dish_price.setText(formatted);
                    et_item_dish_price.setSelection(formatted.length);
                    et_item_dish_price.addTextChangedListener(this);

//                    var cleanString: String = s.toString().replace("[$,.]".toRegex(), "");
//                    if (!cleanString.equals("")) {
//                        var parsed = Integer.parseInt(cleanString);
//
//                        val formatter: NumberFormat = DecimalFormat("#,###")
//
//                        var formatted: String = formatter.format(parsed)
//
//                        current = formatted;
//                        et_item_dish_price.setText(formatted);
//                        et_item_dish_price.setSelection(formatted.length);
//
//                        et_item_dish_price.addTextChangedListener(this);
//
//                    }

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        et_item_dish_eating.setText(orderStatus?.dish_name)
        et_item_owner_name.setText(orderStatus?.owner_name)
        val formatter: NumberFormat = DecimalFormat("#,###")

        var formattedNumber: String = formatter.format(0)
        if (null != orderStatus)
            formattedNumber = formatter.format(orderStatus?.price)

        et_item_dish_price.setText(formattedNumber)

        builder.setView(customview)
            // Add action buttons
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id ->


                    var name = et_item_dish_eating.text.toString()
                    var price =
                        Integer.parseInt(et_item_dish_price.text.toString().replace(",", ""))
                    var owner_name = et_item_owner_name.text.toString()

                    if (null != orderStatus) {
                        // update
                       var  newOrder = OrderStatus(
                            orderStatus.id,
                            name,
                            price,
                            owner_name
                        )
                        viewModel.updateOrder(orderStatus,newOrder)
                    }
                    else
                    {
                        var newOrder =
                            OrderStatus(name, price, owner_name)
                        viewModel.addOrder(newOrder)
                    }


                })

            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()


                })


        var dialog: Dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }



    fun showDialogDeleteConfirmation(orderStatus: OrderStatus) {

        IonAlert(requireContext(), IonAlert.WARNING_TYPE)
            .setTitleText("Are you sure?")
            .setContentText("Won't be able to recover this !")
            .setConfirmText("Yes")
            .setConfirmClickListener(object : IonAlert.ClickListener {
                override fun onClick(sDialog: IonAlert) {

                    viewModel.removeOrder(orderStatus )
                    IonAlert(requireContext(), IonAlert.SUCCESS_TYPE)
                        .setTitleText("Deleted!")
                        .setContentText("Your dish  has been deleted!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null)
                        .show()

                    sDialog?.dismissWithAnimation()



                }
            })
            .setCancelText("Nope!")
            .setCancelClickListener(object : IonAlert.ClickListener {
                override fun onClick(sDialog: IonAlert?) {
                    sDialog?.dismissWithAnimation()
                }
            })
            .show()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DishesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            OrdersFragment().apply {
            }
    }

    override fun clickEdit(dishStatus: com.example.domain.model.OrderStatus) {
        showDialogEdit(dishStatus)
    }

    override fun clickDelete(dishStatus: com.example.domain.model.OrderStatus) {
        showDialogDeleteConfirmation(dishStatus)
    }
}