package com.rsschool.android2021

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigDecimal

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val min = minValue?.text?.trim().toString()
            val max = maxValue?.text?.trim().toString()
            Log.d("app","min: $min\nmax: $max")
            when {
                min.isEmpty() -> {
                    makeToast("the minimum value field cannot be empty!")
                }
                max.isEmpty() -> {
                    makeToast("the maximum value field cannot be empty!")
                }
                BigDecimal(min) < BigDecimal(0) -> {
                    makeToast("minimum value can not be less than 0!")
                }
                BigDecimal(max) < BigDecimal(0) -> {
                    makeToast("maximum value can not be less than 0!")
                }

                BigDecimal(min) > BigDecimal(2147483647) -> {
                    makeToast("the minimum value cannot be greater than the largest possible Int32 value (2147483647)!")
                }

                BigDecimal(max) > BigDecimal(2147483647) -> {
                    makeToast("the maximum value cannot be greater than the largest possible Int32 value (2147483647)!")
                }

                BigDecimal(min) > BigDecimal(max) -> {
                    makeToast("min value can not be more than max value!")
                }
                else -> {
                    (activity as MainActivity).openSecondFragment(min.toInt(), max.toInt())
                }
            }
        }
    }


    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}