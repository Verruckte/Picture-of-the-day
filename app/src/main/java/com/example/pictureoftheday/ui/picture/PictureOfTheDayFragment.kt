package com.example.pictureoftheday.ui.picture

import android.content.Intent
import android.graphics.Typeface.BOLD_ITALIC
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.*
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.MainFragmentBinding
import com.example.pictureoftheday.databinding.MainFragmentStartBinding
import com.example.pictureoftheday.ui.MainActivity
import com.example.pictureoftheday.ui.collapsing_toolbar.CollapsingToolbarActivity
import com.example.pictureoftheday.ui.picture.view_pager.ViewPagerAdapter
import com.example.pictureoftheday.ui.settings.SettingsFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior


const val BOTTOM_SHEET_HEADER = "BottomSheetHeader"
const val BOTTOM_SHEET_CONTENT = "BottomSheetContent"

class PictureOfTheDayFragment : Fragment() {

    private var _binding: MainFragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetHeader: TextView
    private lateinit var bottomSheetContent: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}"
                )
            })
        }
        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        setBottomSheetBehaviour(view.findViewById(R.id.bottom_sheet_container))
        bottomSheetHeader = view.findViewById(R.id.bottom_sheet_description_header)
        bottomSheetContent = view.findViewById(R.id.bottom_sheet_description)
        setBottomAppBar(view)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(null).observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                with(binding) {
                    main.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                }
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Url is empty")
                } else {
                    if (serverResponseData.title != null && serverResponseData.explanation != null) {
                        setBottomSheetTextSpanned(
                            serverResponseData.title,
                            serverResponseData.explanation
                        )
                    }
                }
            }

            is PictureOfTheDayData.Loading -> {
                with(binding) {
                    main.visibility = View.GONE
                    loadingLayout.visibility = View.VISIBLE
                }
            }

            is PictureOfTheDayData.Error -> {
                with(binding) {
                    main.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                }
                toast(data.error.message)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setBottomSheetTextSpanned(title: String, explanation: String) {
        val spannableTitle = SpannableString(title)
        spannableTitle.setSpan(
            BackgroundColorSpan(resources.getColor(R.color.lime_green)),
            0, spannableTitle.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableTitle.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.dark_slate_blue)),
            0, spannableTitle.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableTitle.setSpan(
            StyleSpan(BOLD_ITALIC),
            0, spannableTitle.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableTitle.setSpan(
            UnderlineSpan(),
            0, spannableTitle.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableTitle.setSpan(
            RelativeSizeSpan(1.5f),
            0, spannableTitle.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        bottomSheetHeader.text = spannableTitle

        val spannableContent = SpannableString(explanation)
        spannableContent.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            0, spannableContent.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableContent.setSpan(
            RelativeSizeSpan(1.2f),
            0, spannableContent.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        bottomSheetContent.text = spannableContent
    }

    private fun setBottomSheetBehaviour(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> toast("STATE_DRAGGING")
                    BottomSheetBehavior.STATE_COLLAPSED -> toast("STATE_COLLAPSED")
                    BottomSheetBehavior.STATE_EXPANDED -> toast("STATE_EXPANDED")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> toast("STATE_HALF_EXPANDED")
                    BottomSheetBehavior.STATE_HIDDEN -> toast("STATE_HIDDEN")
                    BottomSheetBehavior.STATE_SETTLING -> toast("STATE_SETTLING")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(context, CollapsingToolbarActivity::class.java)
        intent.putExtra(BOTTOM_SHEET_HEADER, bottomSheetHeader.text)
        intent.putExtra(BOTTOM_SHEET_CONTENT, bottomSheetContent.text)
        when (item.itemId) {
            R.id.app_bar_fav -> startActivity(intent)
            R.id.app_bar_settings -> activity?.apply {
                this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, SettingsFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_search -> toast(getString(R.string.search))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                with(binding) {
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                }
            } else {
                isMain = true
                with(binding) {
                    bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_hamburger_menu_bottom_bar
                    )
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}