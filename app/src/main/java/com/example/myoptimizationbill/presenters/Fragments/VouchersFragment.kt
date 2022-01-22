package com.example.myoptimizationbill.presenters.Fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
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
import com.example.domain.model.VoucherStatus
import com.example.myoptimizationbill.framework.MyViewModelFactory
import com.example.myoptimizationbill.presenters.Adapter.VoucherAdapter
import com.example.myoptimizationbill.utils.Utils
import id.ionbit.ionalert.IonAlert
import java.text.DecimalFormat
import java.text.NumberFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VouchersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VouchersFragment : BaseFragment(), VoucherAdapter.VoucherClickCallback {

    private lateinit var bt_add_voucher: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VoucherAdapter
//    private var list_of_voucher: ArrayList<com.example.domain.model.VoucherStatus> = ArrayList()


    private lateinit var viewModel: VoucherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vouchers, container, false)
//

        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory).get(VoucherViewModel::class.java)
        viewModel.vouchersLiveData.observe(viewLifecycleOwner, Observer {
          Log.d("FRAGMENT_","new size= "+it.size)
            adapter.updateListVoucher(it)
        })
        recyclerView = view.findViewById(R.id.rv_vouchers)
        recyclerView.layoutManager = LinearLayoutManager(this.activity)

        viewModel.test()
//
//
        bt_add_voucher = view.findViewById(R.id.bt_add_voucher)
        bt_add_voucher.setOnClickListener(View.OnClickListener {
            showDialogEdit(null)
        })

        adapter = VoucherAdapter(this)
        recyclerView.adapter = adapter
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VouchersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            VouchersFragment().apply {
            }
    }

    override fun clickEdit(voucherStatus: VoucherStatus) {
        showDialogEdit(voucherStatus)
    }

    override fun clickDelete(voucherStatus: com.example.domain.model.VoucherStatus) {
        showDialogDeleteConfirmation(voucherStatus)
    }

    fun showDialogEdit(voucherStatus: VoucherStatus?) {
        val builder = AlertDialog.Builder(requireContext())
        // Get the layout inflater
        val inflater = this.layoutInflater;

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val customview = inflater.inflate(R.layout.dialog_item_voucher, null)
        var et_voucher_apply_from = customview.findViewById<EditText>(R.id.et_voucher_apply_from)
        var et_voucher_max_apply = customview.findViewById<EditText>(R.id.et_voucher_max_apply)
        var et_voucher_discount = customview.findViewById<EditText>(R.id.et_voucher_discount)


//        var amount_apply_from=et_voucher_apply_from.text.toString().toInt()
//        var amount_max_apply=et_voucher_max_apply.text.toString().toInt()
//        var amount_voucher_discount=et_voucher_discount.text.toString().toDouble()


        if (null != voucherStatus) {
            et_voucher_apply_from.setText(formatCurrency(voucherStatus?.apply_from))
            et_voucher_max_apply.setText(formatCurrency(voucherStatus?.maximum_quantity_apply?.toInt()))
            et_voucher_discount.setText((voucherStatus!!.percent_apply * 100.0).toString())
        }
        et_voucher_apply_from.addTextChangedListener(object : TextWatcher {
            private var current = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().equals(current)) {
                    et_voucher_apply_from.removeTextChangedListener(this);


                    var formatted:String=Utils.formatCurrency(s.toString())

                    et_voucher_apply_from.setText(formatted);
                    et_voucher_apply_from.setSelection(formatted.length);
                    et_voucher_apply_from.addTextChangedListener(this);

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        et_voucher_max_apply.addTextChangedListener(object : TextWatcher {
            private var current = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().equals(current)) {
                    et_voucher_apply_from.removeTextChangedListener(this);

                    var formatted:String=Utils.formatCurrency(s.toString())
                    current = formatted;
                    et_voucher_max_apply.setText(formatted);
                    et_voucher_max_apply.setSelection(formatted.length);
                    et_voucher_max_apply.addTextChangedListener(this);

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        builder.setView(customview)
            // Add action buttons
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id ->
                    var amount_from =
                        (et_voucher_apply_from.text.toString().replace(",", "")).toInt()
                    var amount_max = (et_voucher_max_apply.text.toString().replace(",", "")).toDouble()
                    var amount_discount =
                        (et_voucher_discount.text.toString().replace(",", "")).toDouble()


                    if (null != voucherStatus) {
                        // update
                        var edited_voucherStatus = VoucherStatus(id= voucherStatus.id,
                            apply_from = amount_from,
                            percent_apply = amount_discount/100,
                            maximum_quantity_apply = amount_max
                        )
                        Log.d("FRAGMENT_","update")

                        viewModel.updateVoucher(voucherStatus, edited_voucherStatus)
                    } else
                    {
                        Log.d("FRAGMENT_","add")
                        var newVoucher =
                            VoucherStatus(
                                amount_from,
                                amount_discount,
                                amount_max
                            )
                        viewModel.addVoucher(newVoucher)
                    }

//
                })

            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })


        var dialog: Dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    fun showDialogDeleteConfirmation(voucherStatus: VoucherStatus) {

        IonAlert(requireContext(), IonAlert.WARNING_TYPE)
            .setTitleText("Are you sure?")
            .setContentText("Won't be able to recover this !")
            .setConfirmText("Yes")
            .setConfirmClickListener(object : IonAlert.ClickListener {
                override fun onClick(sDialog: IonAlert) {

                    viewModel.removeVoucher(voucherStatus)
                    IonAlert(requireContext(), IonAlert.SUCCESS_TYPE)
                        .setTitleText("Deleted!")
                        .setContentText("Your voucher  has been deleted!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null)
                        .show()

                    viewModel.removeVoucher(voucherStatus)

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

}