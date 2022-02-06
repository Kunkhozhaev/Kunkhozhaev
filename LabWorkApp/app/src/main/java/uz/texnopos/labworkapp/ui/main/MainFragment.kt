package uz.texnopos.labworkapp.ui.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.labworkapp.R
import uz.texnopos.labworkapp.core.*
import uz.texnopos.labworkapp.core.Constants.NO_INTERNET
import uz.texnopos.labworkapp.data.model.Result
import uz.texnopos.labworkapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var dataList: MutableList<Result> = mutableListOf()
    private var count: Int = 0

    override fun onStart() {
        super.onStart()
        refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        refresh()
        setUpObservers()
    }

    private fun refresh() {
        viewModel.getAllData()
    }

    private fun setUpObservers() {
        viewModel.data.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    hideProgress()
                    dataList = it.data!!.toMutableList()
                    binding.apply {
                        swipeRefresh.setOnRefreshListener {
                            Handler().postDelayed({
                                swipeRefresh.isRefreshing = false
                            }, 2000)
                        }
                        if (dataList.isNotEmpty()) {
                            if (count == 0) {
                                var str = dataList[count].gifURL
                                str = addChar(str, 's', 4)!!
                                Glide
                                    .with(ivGif.context)
                                    .load(str)
                                    .placeholder(R.drawable.developer)
                                    .into(ivGif)

                                tvDescription.text = dataList[count].description
                                btnPrev.isEnabled = false
                            }
                            btnNext.onClick {
                                count++
                                if(count == dataList.size - 1){
                                    btnNext.isEnabled = false
                                } else {
                                    btnNext.isEnabled = true
                                    btnPrev.isEnabled = true
                                }
                                var str = dataList[count].gifURL
                                str = addChar(str, 's', 4)!!
                                    Glide
                                        .with(ivGif.context)
                                        .load(str)
                                        .placeholder(R.drawable.developer)
                                        .into(ivGif)
                                    tvDescription.text = dataList[count].description
                            }
                            btnPrev.onClick {
                                count--
                                if(count == 0){
                                    btnPrev.isEnabled = false
                                } else {
                                    btnPrev.isEnabled = true
                                    btnNext.isEnabled = true
                                }
                                var str = dataList[count].gifURL
                                str = addChar(str, 's', 4)!!
                                    Glide
                                        .with(ivGif.context)
                                        .load(str)
                                        .placeholder(R.drawable.developer)
                                        .into(ivGif)
                                    tvDescription.text = dataList[count].description
                            }
                        }
                        else {
                            toast("Empty Data")
                        }
                    }
                }
                ResourceState.ERROR -> {
                    toast(it.message!!)
                    hideProgress()
                }
                ResourceState.NETWORK_ERROR -> {
                    hideProgress()
                    toast(NO_INTERNET)
                }
            }
        }
    }

    private fun addChar(str: String, ch: Char, position: Int): String? {
        val len = str.length
        val updatedArr = CharArray(len + 1)
        str.toCharArray(updatedArr, 0, 0, position)
        updatedArr[position] = ch
        str.toCharArray(updatedArr, position + 1, position, len)
        return String(updatedArr)
    }
}