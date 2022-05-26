package com.example.utt.hnccomputer.adapter.order

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.Order
import com.example.utt.hnccomputer.entity.model.OrderStatus
import com.example.utt.hnccomputer.extension.convertToDate
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.setColor
import kotlinx.android.synthetic.main.item_cell_order.view.*

class OrderAdapter(context: Context, enableSelectedMode: Boolean) :
    EndlessLoadingRecyclerViewAdapter(context, enableSelectedMode) {

    var onClickOrder: (id: Int) -> Unit = {}

    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(
            parent.inflate(R.layout.layout_load_more)
        )
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {

    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return OrderViewHolder(parent.inflate(R.layout.item_cell_order))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val orderViewHolder = holder as OrderViewHolder
        val order = getItem(position, Order::class.java)

        orderViewHolder.itemView.apply {
            tv_order_id.text = "Mã đơn hàng: ${order?.id}"
            created_order_date.text = "Ngày đặt hàng: ${order?.createdDate?.convertToDate()}"
            total_product_order.text = "Số lượng: ${order?.totalProduct} sản phẩm"
            when (order?.isCheck) {
                OrderStatus.CANCEL -> {
                    order_status.setColor(R.color.base_order_canceled)
                    order_status.text = "Đã hủy đơn hàng lúc ${order.updateDate.convertToDate()}"
                }
                OrderStatus.UNCHECK -> {
                    order_status.setColor(R.color.base_order_unchecked)
                    order_status.text = "Chưa xử lý"
                }
                OrderStatus.CHECK -> {
                    order_status.setColor(R.color.base_order_checked)
                    order_status.text = "Đã nhận đơn hàng lúc ${order.updateDate.convertToDate()}"
                }
            }
        }
    }

    inner class OrderViewHolder(itemView: View) : NormalViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                getItem(adapterPosition, Order::class.java)?.let {
                    onClickOrder(it.id)
                }
            }
        }
    }
}