package com.example.myoptimizationbill.presenters.Fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.model.*
import com.example.myoptimizationbill.R
import com.example.myoptimizationbill.framework.MyViewModelFactory
import com.example.myoptimizationbill.presenters.Adapter.BillAdapter
import com.example.myoptimizationbill.utils.Utils
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.mikhaellopez.circularprogressbar.CircularProgressBar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OptimizingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OptimizingFragment : BaseFragment() {


    lateinit var pieChartView: PieChart
    var pieEntries: ArrayList<PieEntry> = ArrayList()
    var regionalSalesDataArrayList: ArrayList<RegionalSalesData> =
        ArrayList()
    lateinit var recyclerView: RecyclerView


    var isSwitchChartUi: Boolean = true

    lateinit var adapter: BillAdapter
    var list_bill_status: ArrayList<BillStatus> = ArrayList()

    lateinit var viewModel: OptimizingViewModel



    private val vouchers: ArrayList<VoucherStatus> = ArrayList()
    private val orders: ArrayList<OrderStatus> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun makeRequest(
        shipping: Int,
        orders: ArrayList<OrderStatus>,
        vouchers: ArrayList<VoucherStatus>
    ) {

        // var optimizeRequest = OptimizeRequest(Data(shipping, orders, vouchers))
        //  viewModel.calculate(optimizeRequest)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory).get(OptimizingViewModel::class.java)

        var view = inflater.inflate(R.layout.fragment_optimizing, container, false)




        viewModel.orderLiveData.observe(viewLifecycleOwner, Observer {
            orders.clear()
            orders.addAll(it)
        })
        viewModel.vouchersLiveData.observe(viewLifecycleOwner, Observer {
            vouchers.clear()
            vouchers.addAll(it)
        })

        viewModel.chartLiveData.observe(viewLifecycleOwner, Observer {

            var data = it.first
            drawChart(pieChartView, data)

        })

        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer {

            var new_bills = ArrayList<BillStatus>()

            for (bill in it.bills) {
                if (0 != bill.orders.size) {
                    var billStatus = BillStatus(arrayListOf(bill.voucher), bill.orders)
                    new_bills.add(billStatus)
                }

            }
            adapter.updateListBill(new_bills, vouchers, orders)

        })




        recyclerView = view.findViewById(R.id.rv_bills);
        recyclerView.layoutManager = LinearLayoutManager(this.activity)

        adapter = BillAdapter(requireContext())

        recyclerView.adapter = adapter

        pieChartView = view.findViewById(R.id.pieChart)


        viewModel.calculate()

        return view
    }

    fun updateChartValue() {
        pieChartView!!.setUsePercentValues(isSwitchChartUi)
        pieChartView!!.invalidate()
        isSwitchChartUi = !isSwitchChartUi

    }


    fun drawChart(
        pieChart: PieChart,
        list_of_data: ArrayList<Pair<Double, String>>

    ) {

        for (data in list_of_data)
        {
            regionalSalesDataArrayList.add(
                RegionalSalesData(
                    data.second,
                    data.first
                )
            )
        }


//

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(55f);
        pieChart.setTransparentCircleRadius(57f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0f);
        pieChart.setExtraBottomOffset(10f)
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        val l = pieChart!!.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.VERTICAL

        l.setDrawInside(false)
        l.form = Legend.LegendForm.CIRCLE
        l.xEntrySpace = 7f
        l.yEntrySpace = 25f
        l.yOffset = 0f
        l.isWordWrapEnabled = true
        l.setDrawInside(false)
        l.calculatedLineSizes
        l.textSize = 11f


        for (i in regionalSalesDataArrayList.indices) {
            val region = regionalSalesDataArrayList[i].region
            val sales = regionalSalesDataArrayList[i].sales.toFloat()
            pieEntries!!.add(PieEntry(sales, region))
        }
        val pieDataSet = PieDataSet(pieEntries, "Regional Sales")
        var colors: ArrayList<Int> = ArrayList()
        var gray = Color.rgb(174, 174, 174)
        var blue = Color.rgb(3, 71, 171)
        var yellow = Color.rgb(255, 255, 0)
        for (entry: PieEntry in pieEntries) {
            if (entry.label.equals(list_of_data[0].second))
                colors.add(blue)

            if (entry.label.equals(list_of_data[1].second))
                colors.add(gray)

            if (entry.label.equals(list_of_data[2].second))
                colors.add(yellow)
        }
        //pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        pieDataSet.setColors(colors)
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextSize = 16f

        val pieData = PieData(pieDataSet)
        pieChart!!.data = pieData

        pieChart!!.animateXY(2000, 2000)
        pieChart!!.invalidate()
        var total =Math.round( list_of_data[0].first +list_of_data[2].first)
//        var total = list_of_data[0].first + list_of_data[1].first+list_of_data[2].first
        var total_cap=Utils.formatCurrency(total.toString())
        pieChart.setCenterText("Total:" + total_cap);
        pieChart.setDrawCenterText(true)
        pieChart.invalidate();


        pieChart!!.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {

                updateChartValue()

            }

            override fun onNothingSelected() {

            }
        })



    }



    fun drawChart(
        pieChart: PieChart,
        payable_amount: Pair<Double, String>,
        rebate: Pair<Double, String>
    ) {


        regionalSalesDataArrayList.add(
            RegionalSalesData(
                payable_amount.second,
                payable_amount.first
            )
        )
        regionalSalesDataArrayList.add(
            RegionalSalesData(
                rebate.second,
                rebate.first
            )
        )

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(55f);
        pieChart.setTransparentCircleRadius(57f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0f);
        pieChart.setExtraBottomOffset(10f)
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        val l = pieChart!!.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.VERTICAL

        l.setDrawInside(false)
        l.form = Legend.LegendForm.CIRCLE
        l.xEntrySpace = 7f
        l.yEntrySpace = 25f
        l.yOffset = 0f
        l.isWordWrapEnabled = true
        l.setDrawInside(false)
        l.calculatedLineSizes
        l.textSize = 11f


        for (i in regionalSalesDataArrayList.indices) {
            val region = regionalSalesDataArrayList[i].region
            val sales = regionalSalesDataArrayList[i].sales.toFloat()
            pieEntries!!.add(PieEntry(sales, region))
        }
        val pieDataSet = PieDataSet(pieEntries, "Regional Sales")
        var colors: ArrayList<Int> = ArrayList()
        var gray = Color.rgb(174, 174, 174)
        var blue = Color.rgb(3, 71, 171)
        for (entry: PieEntry in pieEntries) {
            if (entry.label.equals(payable_amount.second))
                colors.add(blue)

            if (entry.label.equals(rebate.second))
                colors.add(gray)
        }
        //pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        pieDataSet.setColors(colors)
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextSize = 16f

        val pieData = PieData(pieDataSet)
        pieChart!!.data = pieData

        pieChart!!.animateXY(2000, 2000)
        pieChart!!.invalidate()

        var total = payable_amount.first + rebate.first
        pieChart.setCenterText("Total:" + total);
        pieChart.setDrawCenterText(true)
        pieChart.invalidate();


        pieChart!!.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {

                updateChartValue()

            }

            override fun onNothingSelected() {

            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OptimizingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            OptimizingFragment().apply {

            }
    }
}