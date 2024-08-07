package com.example.attractions.fragment.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.LanguageDefine
import com.example.attractions.R
import com.example.attractions.adapter.AttractionAdapter
import com.example.attractions.databinding.FragmentHomeBinding
import com.example.data.entity.Attraction
import com.example.data.entity.AttractionPlace
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), AttractionAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding


    val attractionAdapter = AttractionAdapter(this@HomeFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        val toolbar: Toolbar = binding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.setOverflowIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.square_button_background
            )
        )

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater!!.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cn -> {
                val code = LanguageDefine.ChineseSimplified.code

                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_es -> {
                val code = LanguageDefine.Spanish.code

                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_en -> {
                val code = LanguageDefine.English.code
                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_id -> {
                val code = LanguageDefine.Indonesian.code
                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_ja -> {
                val code = LanguageDefine.Japanese.code
                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_ko -> {
                val code = LanguageDefine.Korean.code
                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_th -> {
                val code = LanguageDefine.Thai.code
                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_tw -> {
                val code = LanguageDefine.ChineseTraditional.code
                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            R.id.action_vi -> {
                val code = LanguageDefine.Vietnamese.code
                viewModel.setLanguage(code)
//                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservation()

    }

    override fun onItemClick(item: AttractionPlace) {
        val action = HomeFragmentDirections.actHomeDetail(item)
        findNavController().navigate(action)

    }

    fun setupView() {
        val linearlayout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rcvAttractions.apply {
            adapter = attractionAdapter
            itemAnimator = null
            layoutManager = linearlayout
        }
        binding.rcvAttractions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = linearlayout.getChildCount();
                    val totalItemCount = linearlayout.getItemCount();
                    val pastVisiblesItems = linearlayout.findFirstVisibleItemPosition();

//                    if (false == viewModel.loading.va) {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                            viewModel.getMoreAttraction()
//
//                        }
//                    }
                }
            }

        })
    }

    fun setupObservation() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect({
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

            })
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.collect({
                if (true == it)
                    binding.circleLoading.visibility = View.VISIBLE
                else
                    binding.circleLoading.visibility = View.GONE
            })
        }

        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.listAttraction.observe(viewLifecycleOwner,
                object : Observer<Attraction> {
                    override fun onChanged(value: Attraction) {

                        if (0 == value.data.size) {
                            binding.llPlaceHolder.visibility = View.VISIBLE
                            binding.rcvAttractions.visibility = View.GONE
                        } else {
                            binding.llPlaceHolder.visibility = View.GONE
                            binding.rcvAttractions.visibility = View.VISIBLE
                        }

                        attractionAdapter.submitList(value.data)
                    }
                })


        }


    }
}