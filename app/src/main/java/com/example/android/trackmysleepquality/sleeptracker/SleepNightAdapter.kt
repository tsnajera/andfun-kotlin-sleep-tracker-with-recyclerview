/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

// We are creating a SleepNightAdapter class
// We are extending a ListAdapter class now instead of the regular adapter as this list adapter helps us with DiffUtils
// The first param(SleepNight) is the type that this list will hold
// The second is the ViewHolder
// The constructor param takes our item DiffUtil callback
// The clickListener is a listener that the ViewModel Fragment will pass to us
class SleepNightAdapter(val clickListener:SleepNightListener): ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()){

    // This method is to tell RecyclerView how to actually draw an item
    // The RecyclerView calls this when something needs to be drawn and tells us the position of the item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Tell data binding about click listener
        holder.bind(getItem(position)!!, clickListener)

    }

    // Tell RecyclerView how to create a new view holder
    // It calls this when it needs to create a new view holder
    // The parent is the ViewGroup is the that the view holder will be added to
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // Code to define a new view holder that this adapter will use
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item // Set data object from xml to the current sleepnight
            binding.clickListener = clickListener // Pass click listener to the binding object
            binding.executePendingBindings() // Tell binding to execute pending binds. This is for speed.
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>(){

    // Tell DiffUtil how to check if two items are the same.
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId // nightId are unique identifiers so we can use this to know if the SleepNight items/objects are the same
    }

    // Tell DiffUtil how to check if fields in item/object have changed
    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem // Easy way to compare object's fields
    }

}

// Callback for ViewHolder -> ViewModel
class SleepNightListener(val clickListener: (sleepId: Long) -> Unit){
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}
