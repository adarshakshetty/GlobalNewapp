
package com.global.news.presentation.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.global.news.databinding.FragmentDetailsBinding
import com.global.news.presentation.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    override fun setBinding(): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(layoutInflater)

    lateinit var viewModel: MainViewModel
    val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).mainViewModel
        setupUI(view)
        setupObserver();
    }

    private fun setupUI(view: View) {
        val news = args.news
        val isFromFavorite = args.isFromFavorite

        if (isFromFavorite) {
            binding.fab.visibility = View.GONE
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            news.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener {
            viewModel.saveNews(news)
            Snackbar.make(view, "News article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupObserver() {
        viewModel.getFavoriteNews().observe(viewLifecycleOwner, { news ->
            val isAlreadyFavorite = news.any {
                it.title == args.news.title
            }
            if (isAlreadyFavorite) binding.fab.visibility = View.GONE
        })
    }
}