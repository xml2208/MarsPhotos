package com.bignerdranch.android.marsphotos

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bignerdranch.android.marsphotos.databinding.FragmentOverviewBinding
import com.bignerdranch.android.marsphotos.databinding.ItemViewBinding
import com.bignerdranch.android.marsphotos.network.MarsPhoto

class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private lateinit var binding: FragmentOverviewBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.photos.observe(viewLifecycleOwner) { photos ->
            updateUI(photos)
        }
        viewModel.status.observe(viewLifecycleOwner) { status ->
            bindStatus(status)
        }

    }

    private fun updateUI(photos: List<MarsPhoto>) {
        binding.rView.layoutManager = GridLayoutManager(context, 3)
        binding.rView.adapter = PhotoAdapter(photos)
    }

    private fun bindStatus(status: MarsApiStatus) {
        when (status) {
            MarsApiStatus.LOADING -> {
                binding.statusImage.visibility = View.VISIBLE
                binding.statusImage.setImageResource(R.drawable.loading_animation)
            }
            MarsApiStatus.ERROR -> {
                binding.statusImage.visibility = View.VISIBLE
                binding.statusImage.setImageResource(R.drawable.error)
            }
            MarsApiStatus.DONE -> {
                binding.statusImage.visibility = View.GONE

            }
        }
    }

    private class PhotoAdapter(private val photos: List<MarsPhoto>) :
        RecyclerView.Adapter<PhotoHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            return PhotoHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val photo = photos[position]
            holder.asd(photo)
        }

        override fun getItemCount() = photos.size

        companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
            override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
                return oldItem.imgSrcUrl == newItem.imgSrcUrl
            }
        }
    }

    private class PhotoHolder(private var binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun asd(marsPhoto: MarsPhoto) {
            binding.marsImage.load(marsPhoto.imgSrcUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
    }
}

