package com.example.justordinarydiaryapp.presentation.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentEditDiaryBinding
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.presentation.home.HomeActivity
import com.example.justordinarydiaryapp.utils.DateTimeHelper
import com.example.justordinarydiaryapp.utils.ValidationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class EditDiaryFragment : BaseFragment<FragmentEditDiaryBinding>() {

    private val viewModel: DiaryViewModel by viewModel()

    private val diaryData by lazy { arguments?.getParcelable<Diary>(KEY_TRANSFER_DIARY) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditDiaryBinding
        get() = FragmentEditDiaryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initView()
    }

    private fun initObserver() {
        viewModel.editDiaryLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    ProgressDialog.show(requireContext())
                }

                is ResultWrapper.Success -> {
                    ProgressDialog.dismiss()
                    showSuccessDialog(
                        desc = "Diary Updated Successfully",
                        onDismiss = { HomeActivity.launchIntent(requireContext()) },
                        isCancelable = true
                    )
                }

                is ResultWrapper.Error -> {
                    ProgressDialog.dismiss()
                    showErrorDialog(it.message)
                }
            }
        }
    }

    private fun initView() {

        binding.txtTitleDiary.setText(diaryData?.title.toString())
        binding.txtContentDiary.setText(diaryData?.content.toString())

        binding.txtContentDiary.doOnTextChanged { text, _, _, _ ->
            val characterCount = text?.length
            val maxLength = 500
            if (characterCount != null) {
                if (characterCount <= maxLength) {
                    binding.tvCount.text = String.format("%d/%d", characterCount, maxLength)
                }
            }
        }

        val text: String? = DateTimeHelper.getStringFromCalendar(
            Calendar.getInstance(),
            DateTimeHelper.FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE
        )
        binding.tvCurrentTime.text = text

        binding.apply {
            cvSave.setOnClickListener {
                if (isDataValid) {
                    viewModel.editDairy(
                        diaryData?.id.toString(),
                        DiaryRequest(
                            txtTitleDiary.text.toString(),
                            txtContentDiary.text.toString()
                        )
                    )
                } else {
                    showToast("Please fill title and content")
                }
            }
        }
    }

    private val isDataValid: Boolean
        get() {
            binding.apply {
                var isValid = true
                txtTitleDiary.clearFocus()
                txtContentDiary.clearFocus()

                if (ValidationHelper.isBlank(txtTitleDiary)) {
                    txtTitleDiary.requestFocus()
                    isValid = false
                } else if (ValidationHelper.isBlank(txtContentDiary)) {
                    txtContentDiary.requestFocus()
                    isValid = false
                }

                return isValid
            }
        }

    companion object {

        private const val KEY_TRANSFER_DIARY = "KEY_TRANSFER_DIARY"

        fun newInstance(diary: Diary): EditDiaryFragment {
            return EditDiaryFragment().apply {
                val args = Bundle()
                args.putParcelable(KEY_TRANSFER_DIARY, diary)
                arguments = args
            }
        }
    }
}