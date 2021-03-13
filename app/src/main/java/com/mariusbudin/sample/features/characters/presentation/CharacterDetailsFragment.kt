package com.mariusbudin.sample.features.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mariusbudin.sample.core.extension.loadCircle
import com.mariusbudin.sample.core.platform.Resource
import com.mariusbudin.sample.core.platform.autoCleared
import com.mariusbudin.sample.databinding.CharacterDetailsFragmentBinding
import com.mariusbudin.sample.features.characters.presentation.CharactersFragment.Companion.PARAM_ID
import dagger.hilt.android.AndroidEntryPoint

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

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    it.data?.let { character ->
                        binding.title.text = character.name
                        binding.status.text = character.status
                        binding.image.loadCircle(character.image)
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progress.visibility = View.VISIBLE
            }
        })
    }
}