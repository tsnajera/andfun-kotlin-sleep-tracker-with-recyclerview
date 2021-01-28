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

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

// We are creating a SleepNightAdaper class
// This class extends the RecyclerView.Adapter class so Android knows what type of adapter we are trying to create.
// We must also say what kind of view holder that this recycler adapter will hold. Recycler interacts with view holders, not the views themselves. This is a generic holder, not a custom.
// We will have a compile error because we need to implement a few methods, so that the RecyclerView knows how to use this adapter.
class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>(){

    // This will hold the data that our adapter is adapting for the Recyclerview to use.
    // RecyclerView will not use this data directly
    var data = listOf<SleepNight>()
        set(value){ // this custom setter is for when data in the list changes and the RecyclerView needs to know to change the data on the screen
            field = value
            notifyDataSetChanged() // This will cause an entire redraw of all items, which is not very efficient
        }

    // This is one of the methods that need to be overwritten
    // Since the data list will hold the data that needs to be displayed, we will return the size of this list for this method
    override fun getItemCount(): Int {
        return data.size
    }

    // This method is to tell RecyclerView how to actually draw an item
    // The RecyclerView calls this when something needs to be drawn and tells us the position of the item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position] // create variable for current item in SleepNight list
        val res = holder.itemView.context.resources
        holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)

        holder.qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })

    }

    // Tell RecyclerView how to create a new view holder
    // It calls this when it needs to create a new view holder
    // The parent is the ViewGroup is the that the view holder will be added to
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
        return ViewHolder(view)

    }

    // Code to define a new view holder that this adapter will use
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
    }

}
