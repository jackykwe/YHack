package com.yhack.tutoree.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yhack.tutoree.databinding.RvItemEmptyBinding
import com.yhack.tutoree.databinding.RvItemHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val EMPTY = 0
private const val PERSON = 1

class HomeRVAdapter(private val itemOnClickListener: HomeOnClickListener) :
    ListAdapter<HomeRVItem, RecyclerView.ViewHolder>(HomeRVItemDiffCallback()) {

    fun submitList2(pidList: List<Int>) {
        CoroutineScope(Dispatchers.Default).launch {
            val submittable = if (pidList.isEmpty()) {
                listOf(HomeRVItem.EmptyCard())
            } else {
                pidList.map { HomeRVItem.PersonCard(it) }
            }

            withContext(Dispatchers.Main) {
                submitList(submittable)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeRVItem.PersonCard -> PERSON
            is HomeRVItem.EmptyCard -> EMPTY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY -> EmptyViewHolder.inflateAndCreateViewHolderFrom(parent)
            PERSON -> PersonViewHolder.inflateAndCreateViewHolderFrom(parent)
            else -> throw IllegalArgumentException("Illegal viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PersonViewHolder -> {
                val item = getItem(position) as HomeRVItem.PersonCard
                holder.rebind(item.pid, itemOnClickListener)
            }
        }
    }

    class EmptyViewHolder private constructor(private val binding: RvItemEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateAndCreateViewHolderFrom(parent: ViewGroup): EmptyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvItemEmptyBinding.inflate(layoutInflater, parent, false)
                return EmptyViewHolder(binding)
            }
        }
    }

    class PersonViewHolder private constructor(private val binding: RvItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun rebind(pid: Int, itemOnClickListener: HomeOnClickListener) {
            binding.pid = pid
            binding.onClickListener = itemOnClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun inflateAndCreateViewHolderFrom(parent: ViewGroup): PersonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvItemHomeBinding.inflate(layoutInflater, parent, false)
                return PersonViewHolder(binding)
            }
        }
    }
}

class HomeOnClickListener(val clickListener: (view: View, pid: Int) -> Unit) {
    fun onClick(view: View, pid: Int) = clickListener(view, pid)
}

class HomeRVItemDiffCallback : DiffUtil.ItemCallback<HomeRVItem>() {
    override fun areItemsTheSame(
        oldItem: HomeRVItem,
        newItem: HomeRVItem
    ): Boolean {
        return oldItem.rvItemId == newItem.rvItemId
    }

    override fun areContentsTheSame(
        oldItem: HomeRVItem,
        newItem: HomeRVItem
    ): Boolean {
        return oldItem == newItem
    }
}

sealed class HomeRVItem {
    abstract val rvItemId: Int

    data class PersonCard(val pid: Int) : HomeRVItem() {
        override val rvItemId: Int = pid
    }

    class EmptyCard : HomeRVItem() {
        override val rvItemId: Int = Int.MIN_VALUE
    }
}