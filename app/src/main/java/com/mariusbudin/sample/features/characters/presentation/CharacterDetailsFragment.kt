package com.mariusbudin.sample.features.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.mariusbudin.sample.core.extension.loadCircle
import com.mariusbudin.sample.core.platform.autoCleared
import com.mariusbudin.sample.databinding.CharacterDetailsFragmentBinding
import com.mariusbudin.sample.features.characters.presentation.CharactersFragment.Companion.PARAM_ID
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var binding: CharacterDetailsFragmentBinding by autoCleared()
    private val viewModel: CharacterDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        arguments?.getInt(PARAM_ID)?.let { viewModel.init(it) }
    }

    private fun setupObservers() {
        viewModel.character.observe(viewLifecycleOwner, {

            binding.progress.visibility = View.GONE
            it?.let { character ->
                binding.title.text = character.name
                binding.status.text = character.status
                binding.image.loadCircle(character.image)
            }
        })
    }
}