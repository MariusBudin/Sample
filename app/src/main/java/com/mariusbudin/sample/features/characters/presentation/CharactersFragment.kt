package com.mariusbudin.sample.features.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusbudin.sample.R
import com.mariusbudin.sample.core.platform.autoCleared
import com.mariusbudin.sample.databinding.CharactersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private var binding: CharactersFragmentBinding by autoCleared()
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter { navigateToDetails(it) }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.characters.observe(viewLifecycleOwner) { chars ->
            binding.progress.visibility = View.GONE
            adapter.submitData(lifecycle, chars)
        }
    }

    private fun navigateToDetails(id: Int) {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment,
            bundleOf(PARAM_ID to id)
        )
    }

    companion object {
        const val PARAM_ID = "params.id"
    }
}