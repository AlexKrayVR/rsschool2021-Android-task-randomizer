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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null

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
            val max = maxValue?.text?.trim().toString()
            val min = minValue?.text?.trim().toString()
            when {
                min.isEmpty() -> {
                    Toast.makeText(context, "write min value!", Toast.LENGTH_SHORT).show()
                }
                max.isEmpty() -> {
                    Toast.makeText(context, "write max value!", Toast.LENGTH_SHORT).show()
                }
                min > max -> {
                    Toast.makeText(
                        context,
                        "min value can not be more than max value!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                else -> {
                    (activity as MainActivity).openSecondFragment(min.toInt(), max.toInt())
//                    val secondFragment: SecondFragment =
//                        SecondFragment.newInstance(min.toInt(), max.toInt())
//                    val transaction: FragmentTransaction =
//                        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
//                    transaction.replace(R.id.container, secondFragment).commit()
                }
            }
        }
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