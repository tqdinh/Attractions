package com.example.attractions.fragment.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.adapter.AttractionAdapter
import com.example.attractions.databinding.FragmentHomeBinding
import com.example.data.entity.Attraction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
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


//
//        // Enable the back button
//        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
//            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//            (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle("Fragment Title")
//        }
//
//
        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater!!.inflate(com.example.attractions.R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
//            R.id.action_settings -> {
//                Toast.makeText(activity, "Settings clicked", Toast.LENGTH_SHORT).show()
//                true
//            }
//
//            R.id.action_about -> {
//                Toast.makeText(activity, "About clicked", Toast.LENGTH_SHORT).show()
//                true
//            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservation()
        viewModel.getListAttraction("vi", 1)
    }

    override fun onItemClick(item: Attraction) {

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
                if (dy > 0) //check for scroll down
                {
                    val visibleItemCount = linearlayout.getChildCount();
                    val totalItemCount = linearlayout.getItemCount();
                    val pastVisiblesItems = linearlayout.findFirstVisibleItemPosition();

                    if (false == viewModel.loading.value) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            Log.d("SCROOL_ITEM", "Last Item Wow !");

                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(requireContext(), "Last", Toast.LENGTH_SHORT).show()
                            }

                            viewModel.getMoreAttraction()

                        }
                    }
                }
            }

        })
    }

    fun setupObservation() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.observe(viewLifecycleOwner, object : Observer<Boolean> {
                override fun onChanged(value: Boolean) {
                    binding.circleLoading.visibility = View.GONE
                    if (true == value)
                        binding.circleLoading.visibility = View.VISIBLE
                }

            })
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.listAttraction.observe(viewLifecycleOwner,
                object : Observer<Attraction> {
                    override fun onChanged(value: Attraction) {

                        attractionAdapter.submitList(value.data)
                        attractionAdapter.notifyDataSetChanged()

                    }
                })

        }
    }
}