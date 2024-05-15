package com.example.attractions.fragment.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.attractions.R
import com.example.attractions.databinding.FragmentDetailBinding
import com.example.data.entity.AttractionPlace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    lateinit var attractionPlace: AttractionPlace

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        arguments?.apply {
            attractionPlace = this.get("attractionplace") as AttractionPlace
        }


        val toolbar: Toolbar = binding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                findNavController().navigateUp()
            }
        })


        val actionBar: ActionBar? = (activity as AppCompatActivity?)!!.supportActionBar

        actionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setTitle(attractionPlace.name)

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            onBackPressedCallback
        );


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
    }

    fun setupView() {
        attractionPlace?.apply {
            Glide.with(requireContext())
                .load(this.avatar)
                .placeholder(R.drawable.img_placeholder)
                .centerCrop()
                .into(binding.ivAvatar)

            binding.tvIntro.text = this.introduction
            binding.tvName.text = this.name
            binding.tvHyperlink.text = this.official_site

            binding.tvAddress.text = this.address
            binding.tvLatestUpdate.text = this.modified
        }
    }

    fun setupListener() {
        binding.tvHyperlink.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val action = DetailFragmentDirections.actDetailWebview(attractionPlace.official_site)
                findNavController().navigate(action)
            }

        })
    }
}