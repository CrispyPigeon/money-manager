package com.ds.money_manager.feature.costtype

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentChooseCostTypeBinding
import com.ds.money_manager.databinding.FragmentChooseWalletBinding
import com.ds.money_manager.feature.adapters.CostTypesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseCostTypeFragment :
    DialogsSupportFragment<FragmentChooseCostTypeBinding, ChooseCostTypeViewModel>() {

    var costTypesAdapter: CostTypesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCostTypes()
    }

    override fun initViews() {
        configureCostTypeRv()
    }

    override fun initListeners() {
        super.initListeners()

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }

        viewModel.costTypes.observe(viewLifecycleOwner) {
            costTypesAdapter!!.setItems(it)
        }
    }

    private fun configureCostTypeRv() {
        costTypesAdapter = CostTypesAdapter(requireContext())
        binding.recyclerViewCostTypes.adapter = costTypesAdapter
        binding.recyclerViewCostTypes.layoutManager = LinearLayoutManager(requireContext())
    }
}