package com.herald.mazaadyapp.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.herald.mazaadyapp.R
import com.herald.mazaadyapp.common.Constants
import com.herald.mazaadyapp.common.Utils
import com.herald.mazaadyapp.databinding.ActivityMainBinding
import com.herald.mazaadyapp.domain.models.AllCategories
import com.herald.mazaadyapp.domain.models.AllCategories.Category.Children
import com.herald.mazaadyapp.presentation.categories.CategoriesViewModel
import com.herald.mazaadyapp.presentation.options.OptionsViewModel
import com.herald.mazaadyapp.presentation.properties.PropertiesAdapter
import com.herald.mazaadyapp.presentation.properties.PropertiesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val propertiesViewModel: PropertiesViewModel by viewModels()
    private val optionsViewModel: OptionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var allCategories = AllCategories(emptyList())
        var subCategories = emptyList<Children>()

        categoriesViewModel.state.observe(this) { state ->
            binding.loadingMain.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE

            if (state.categories != null) {
                allCategories = state.categories
                binding.spinCategories.setItems(allCategories.categories.map { it.name }
                    .toTypedArray())
            }

            if (state.error != null) {
                Utils.showErrorDialog(state.error, this) { categoriesViewModel.getCategories() }
            }
        }

        binding.spinCategories.setOnItemClickListener { index ->
            subCategories = allCategories.categories[index].children
            Constants.arrAllItems["التصنيف"] = allCategories.categories[index].name
            binding.spinSubCategories.apply {
                clear()
                setText("التصنيف الفرعي")
                setItems(subCategories.map { it.name }.toTypedArray())
            }
        }

        binding.spinSubCategories.setOnItemClickListener { index ->
            Constants.arrAllItems["التصنيف الفرعي"] = subCategories[index].name
            propertiesViewModel.getProperties(subCategories[index].id)
        }

        propertiesViewModel.state.observe(this) { state ->
            binding.loadingMain.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE
            binding.recyclerChildren.adapter =
                state.properties?.data?.let { PropertiesAdapter(it, optionsViewModel, this) }
            if (state.error != null) {
                Utils.showErrorDialog(
                    state.error,
                    this
                ) { propertiesViewModel.getProperties(binding.spinSubCategories.id) }
            }
        }

        binding.btnSubmit.setOnClickListener {
            // TODO: fix final array bug and show items in table view
            val sb: StringBuilder = StringBuilder()
            Constants.arrAllItems.forEach{ item ->
                sb.append("${item.key}<------>${item.value} \n")
            }
            binding.resultText.text = sb
        }
    }
}