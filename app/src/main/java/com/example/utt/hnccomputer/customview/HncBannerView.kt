package com.example.utt.hnccomputer.customview

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.utt.hnccomputer.adapter.banner.BannerPagerAdapter
import com.example.utt.hnccomputer.adapter.banner.DotAdapter
import com.example.utt.hnccomputer.databinding.ViewHncBannerBinding
import com.example.utt.hnccomputer.entity.model.Banner
import com.example.utt.hnccomputer.utils.DeviceUtil
import kotlin.math.abs

class HncBannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewHncBannerBinding = ViewHncBannerBinding.inflate(LayoutInflater.from(context), this, false)

    private var dotAdapter: DotAdapter = DotAdapter(context)
    private var bannerAdapter: BannerPagerAdapter = BannerPagerAdapter(context)

    private val temp = arrayListOf(1, 2, 3, 4, 5)

    private val sliderHandler = Handler()

    private val paddingHorizontal = DeviceUtil.convertToDp(context, 20)

    companion object {
        private const val MAX_ALPHA = 1.0f
        private const val MIN_ALPHA = 0.5f
        private const val MAX_SCALE = 1f
        private const val SCALE_PERCENT = 0.8f
        private const val MIN_SCALE = SCALE_PERCENT * MAX_SCALE
    }

    init {
        addView(binding.root)

        val sliderRunnable = Runnable {
            if (bannerAdapter.itemCount.minus(1) == binding.bannerPager.currentItem) {
                binding.bannerPager.currentItem = 0
            } else {
                binding.bannerPager.currentItem = binding.bannerPager.currentItem.plus(1)
            }
        }

        binding.bannerPager.adapter = bannerAdapter
        binding.rcvDot.adapter = dotAdapter
        binding.rcvDot.layoutManager = GridLayoutManager(context, temp.size)

        dotAdapter.refresh(temp)
        dotAdapter.setSelectedItem(0,true)

        binding.bannerPager.apply {
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val screenWidth = resources.displayMetrics.widthPixels
            val nextItemTranslationX = 19f * screenWidth / 300
            offscreenPageLimit = 3
            setPageTransformer { page, position ->
                val absPosition = abs(position)
                setPadding(paddingHorizontal, 0, paddingHorizontal, 0)
                val scale = MAX_SCALE - (MAX_SCALE - MIN_SCALE) * absPosition
                page.alpha = MAX_ALPHA - (MAX_ALPHA - MIN_ALPHA) * absPosition
                page.translationX = -position * nextItemTranslationX
                page.scaleX = scale
                page.scaleY = scale
            }
        }

        binding.bannerPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
                dotAdapter.updateTabSelected(position)
            }
        })
    }

    fun setBanner(banner: List<Banner>) {
        bannerAdapter.refresh(banner)
    }
}