package com.example.todolist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_ID = -1
        private const val UNDEFINED_SCREEN_MODE = ""
        private const val TAG = "ShopItemActivity"
        private val CLEARED_ERROR = null

        fun getAddFragmentInstance(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun getEditFragmentInstance(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }

    private var screenMode: String = UNDEFINED_SCREEN_MODE
    private var shopItemId: Int = UNDEFINED_ID
    private lateinit var tilName: TextInputLayout
    private lateinit var etName: TextInputEditText
    private lateinit var tilCount: TextInputLayout
    private lateinit var etCount: TextInputEditText
    private lateinit var bSave: MaterialButton
    private lateinit var shopItemViewModel: ShopItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parseParams()
        return inflater.inflate(
            R.layout.fragment_shop_item,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupViewModel()
        setupViewModelOnClickListener()
        choseActionScreenMode()
        setupViewModelObservable()
        setupViewDoOnTextChanged()
    }

    private fun setupViewDoOnTextChanged() {
        etName.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                tilName.error = getString(R.string.folder_can_t_be_empty)
            } else {
                tilName.error = CLEARED_ERROR
            }
        }

        etCount.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                tilCount.error = getString(R.string.folder_can_t_be_empty)
            } else {
                tilCount.error = CLEARED_ERROR
            }
        }
    }

    private fun setupViewModelObservable() {
        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            when (it) {
                true -> tilName.error = getString(R.string.there_is_some_problem_with_name)
                false -> tilName.error = null
            }
        }

        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
            when (it) {
                true -> tilCount.error = getString(R.string.there_is_some_problem_with_number)
                false -> tilCount.error = null
            }
        }
    }

    private fun choseActionScreenMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        etName = view.findViewById(R.id.et_name)
        tilCount = view.findViewById(R.id.til_count)
        etCount = view.findViewById(R.id.et_count)
        bSave = view.findViewById(R.id.b_save)
    }

    private fun launchAddMode() {
        setupSaveButtonOnClickListener()
    }

    private fun setupSaveButtonOnClickListener() {
        bSave.setOnClickListener {
            val nameInput = etName.text.toString()
            val countInput = etCount.text.toString()

            shopItemViewModel.addShopItemToMainList(nameInput, countInput)
        }
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItemById(shopItemId)

        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        bSave.setOnClickListener {
            shopItemViewModel.editShopItemFromMainList(

                etName.text.toString(),
                etCount.text.toString()
            )
        }
    }

    private fun parseParams() {
        val arg = requireArguments()

        if (!arg.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = arg.getString(SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!arg.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = arg.getInt(
                SHOP_ITEM_ID,
                UNDEFINED_ID
            )
        }
    }

    private fun setupViewModel() {
        shopItemViewModel =
            ViewModelProvider(this@ShopItemFragment)[ShopItemViewModel::class.java]
    }

    private fun setupViewModelOnClickListener() {
        shopItemViewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }
}